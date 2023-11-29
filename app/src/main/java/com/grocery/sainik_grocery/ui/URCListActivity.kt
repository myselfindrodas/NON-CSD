package com.grocery.sainik_grocery.ui

import android.content.ContentValues
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sainikgrocerycustomer.data.model.Urc
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.base.BaseActivity
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.model.noncsdmodel.Data
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.ActivityUrclistBinding
import com.grocery.sainik_grocery.ui.adapter.UrcListAdapter
import com.grocery.sainik_grocery.utils.Shared_Preferences
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel

class URCListActivity : BaseActivity(), UrcListAdapter.OnItemClickListener {

    lateinit var binding: ActivityUrclistBinding
    private lateinit var viewModel: CommonViewModel
    lateinit var urcListAdapter: UrcListAdapter
    val urcModelArrayList: ArrayList<Data> = arrayListOf()
    override fun resourceLayout(): Int {
        return R.layout.activity_urclist
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityUrclistBinding
    }

    override fun setFunction() {
        with(binding) {

            val vm: CommonViewModel by viewModels {
                CommonModelFactory(ApiHelper(ApiClient.apiService))
            }

            viewModel = vm


            ivBack.setOnClickListener {

                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                onBackPressedDispatcher.onBackPressed()
            }


            btnContinue.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                var isSelected=false
                urcListAdapter.getURCList().forEach {item->
                    if (item.isSelected){
                        isSelected=true
                        MainActivity.URCName=item.counterName
                        return@forEach
                    }

                }
                val intent = Intent(this@URCListActivity, MainActivity::class.java)
                if (isSelected) {
                    startActivity(intent)
                    finish()
                }
                else
                    Toast.makeText(
                        this@URCListActivity,
                        "Please select an NON CSD from the list above.",
                        Toast.LENGTH_SHORT
                    ).show()
            }
            setupRecyclewrView()
//            UrcList()

        }
    }


    private fun setupRecyclewrView() {
        urcListAdapter = UrcListAdapter(this, urcModelArrayList, this@URCListActivity)
        val mLayoutManager: RecyclerView.LayoutManager = GridLayoutManager(this, 1)
        binding.rvUrc.layoutManager = mLayoutManager
        binding.rvUrc.itemAnimator = DefaultItemAnimator()
        binding.rvUrc.adapter = urcListAdapter
        NonCSDList()

    }


    private fun NonCSDList() {
        if (Utilities.isNetworkAvailable(this)) {

            viewModel.getnonCSDlist().observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideProgressDialog()
                            resource.data?.data?.let { itNoncsdlist ->


                                urcListAdapter.updateData(itNoncsdlist)
                                /* with(fragmentManageDriverBinding) {

                                     Glide.with(mainActivity)
                                         .load(itProfileInfo.profile.profileImage)
                                         .timeout(6000)
                                         .error(com.app.conectar.R.drawable.logo)
                                         .placeholder(com.app.conectar.R.drawable.logo)
                                         .into(PrfImg)


                                 }*/


                            }

                        }
                        Status.ERROR -> {
                            hideProgressDialog()
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
                            showProgressDialog()
                        }

                    }

                }
            }

        } else {

            Toast.makeText(this, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()

        }

    }


    override fun onClick(position: Int, view: View, mUrcModelArrayList: ArrayList<Data>) {
        mUrcModelArrayList.forEach { item ->
            item.isSelected = false
        }
        mUrcModelArrayList[position].isSelected = true
        urcListAdapter.updateData(mUrcModelArrayList)
    }


    fun showProgressDialog() {
        binding.rlLoading.visibility = View.VISIBLE
    }

    fun hideProgressDialog() {
        binding.rlLoading.visibility = View.GONE
    }

}