package com.grocery.sainik_grocery.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.model.categorymodel.CategoryRequest
import com.grocery.sainik_grocery.data.model.categorymodel.Data
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.FragmentPartyProductBinding
import com.grocery.sainik_grocery.databinding.FragmentSearchBinding
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.ui.adapter.CategoryListAdapter
import com.grocery.sainik_grocery.ui.adapter.CategoryListAdapter2
import com.grocery.sainik_grocery.utils.ItemOffsetDecoration
import com.grocery.sainik_grocery.utils.Shared_Preferences
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel

class PartyProduct : Fragment(), CategoryListAdapter2.OnItemClickListener {
    lateinit var mainActivity: MainActivity
    private lateinit var viewModel: CommonViewModel
    lateinit var binding: FragmentPartyProductBinding
    private var categoryListAdapter2: CategoryListAdapter2? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_party_product, container, false)
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

        binding.topnav.btnBack.setOnClickListener {
            if (Utilities.isClickRecently()) {
                return@setOnClickListener
            }
            mainActivity.onBackPressedDispatcher.onBackPressed()
        }


        mainActivity.setBottomNavigationVisibility(true)

        binding.topnav.tvNavtitle.text = "Category"


        categoryListAdapter2 = CategoryListAdapter2(mainActivity, this@PartyProduct)
        binding.rvpartyProductList.adapter = categoryListAdapter2
        binding.rvpartyProductList.layoutManager = GridLayoutManager(mainActivity, 1)
        val itemDecoration = ItemOffsetDecoration(mainActivity, R.dimen.photos_list_spacing1)
        binding.rvpartyProductList.addItemDecoration(itemDecoration)
        categorylist()


    }


    private fun categorylist() {
        if (Utilities.isNetworkAvailable(mainActivity)) {

            viewModel.categorylist(CategoryRequest(productMainCategoryId = Shared_Preferences.getMaincatid().toString()))
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                resource.data.let { itResponse ->

                                    if (itResponse?.status == true) {

                                        categoryListAdapter2!!.updateData(itResponse.data)

                                    }

                                }

                                mainActivity.hideProgressDialog()
                            }

                            Status.ERROR -> {
                                mainActivity.hideProgressDialog()
                                val builder = AlertDialog.Builder(mainActivity)
                                builder.setMessage(it.message)
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->

                                    dialog.cancel()

                                }
                                val alert = builder.create()
                                alert.show()
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

    override fun onClickCategory(position: Int, view: View, mProductModelArrayList: Data) {

        val bundle = Bundle()
        bundle.putString("viewalltype", "categoryProduct")
        bundle.putString("catName", mProductModelArrayList.name)
        bundle.putString("catId", mProductModelArrayList.id)
        val navController = Navigation.findNavController(view)
        navController.navigate(R.id.nav_partyproductlist, bundle)

    }

}