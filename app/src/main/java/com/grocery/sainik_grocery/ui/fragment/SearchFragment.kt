package com.grocery.sainik_grocery.ui.fragment

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.model.productlistmodel.Data
import com.grocery.sainik_grocery.data.model.productlistmodel.ProductListRequest
import com.grocery.sainik_grocery.data.model.searchmodel.SearchRequest
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.FragmentSearchBinding
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.ui.adapter.SearchAdapter
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel
import java.util.Locale
import java.util.Objects

class SearchFragment : Fragment(), SearchAdapter.SearchItemClickListener {

    private lateinit var binding: FragmentSearchBinding
    private lateinit var viewModel: CommonViewModel
    private lateinit var mainActivity: MainActivity
    private val REQUEST_CODE_SPEECH_INPUT = 1
    var searchAdapter: SearchAdapter ?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        val root = binding.root
        mainActivity = activity as MainActivity

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm


        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topnav.tvNavtitle.text = "Search Product"

        binding.topnav.btnBack.setOnClickListener {
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }


        searchAdapter = SearchAdapter(mainActivity, this@SearchFragment)
        binding.rvSearchList.layoutManager = LinearLayoutManager(mainActivity)
        binding.rvSearchList.adapter = searchAdapter


        binding.ivMic.setOnClickListener {

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)

            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )

            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE,
                Locale.getDefault()
            )

            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say Something...")

            try {
                startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
            } catch (e: Exception) {
                Toast
                    .makeText(
                        mainActivity, " " + e.message,
                        Toast.LENGTH_SHORT
                    )
                    .show()
            }
        }



        binding.etSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {


            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if (s?.length!! > 1) {
                    binding.rvSearchList.visibility = View.VISIBLE
                    search(s.toString())
//                    dashboard(Shared_Preferences.getUrcid().toString(), s.toString())
                } else {
                    binding.rvSearchList.visibility = View.GONE

//                    dashboard(Shared_Preferences.getUrcid().toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })


    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE_SPEECH_INPUT) {
            if (resultCode == Activity.RESULT_OK && data != null) {

                val res: ArrayList<String> = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>

                binding.etSearch.setText(Objects.requireNonNull(res)[0])
            }
        }
    }



    private fun search(keyword:String){

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.search(SearchRequest("", keyword, "100", "0", )).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            if (resource.data?.status == true) {
                                searchAdapter?.updateData(resource.data.data)
                                if (resource.data.data.isEmpty()) {

                                    binding.nodata.root.visibility = View.VISIBLE
                                } else {

                                    binding.nodata.root.visibility = View.GONE

                                }
                            } else {
                                searchAdapter?.updateData(arrayListOf())
                                if (resource.data!!.data.isEmpty()) {

                                    binding.nodata.root.visibility = View.VISIBLE
                                } else {

                                    binding.nodata.root.visibility = View.GONE

                                }

//                                fragmentProductListBinding.nodata.root.visibility = View.VISIBLE
                            }
                            mainActivity.hideProgressDialog()
//                            CartList(isFirstPage)


                        }

                        Status.ERROR -> {
                            mainActivity.hideProgressDialog()
                            Log.d(ContentValues.TAG, "print-->" + resource.data?.status)

//                            if (it.message!!.contains("401", true)) {
//                                val builder = AlertDialog.Builder(this@LoginemailActivity)
//                                builder.setMessage("Invalid Employee Id / Password")
//                                builder.setPositiveButton(
//                                    "Ok"
//                                ) { dialog, which ->
//
//                                    dialog.cancel()
//
//                                }
//                                val alert = builder.create()
//                                alert.show()
//                            } else
//                                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()

                        }

                        Status.LOADING -> {
                            mainActivity.showProgressDialog()
                        }

                    }

                }
            }

        } else {

            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT)
                .show()

        }
    }

    override fun searchListOnClick(position: Int, list: ArrayList<Data>, view: View) {

        val bundle = Bundle()
        bundle.putString("productid", list[position].id)
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.nav_productdetails, bundle)

    }


}