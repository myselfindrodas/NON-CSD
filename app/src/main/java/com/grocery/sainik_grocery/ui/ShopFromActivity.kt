package com.grocery.sainik_grocery.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.GridLayoutManager
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.base.BaseActivity
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.model.productmaincategorymodel.Data
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.ActivityShopFromBinding
import com.grocery.sainik_grocery.ui.adapter.AddressAdapter
import com.grocery.sainik_grocery.ui.adapter.ChooseMainCategoryAdapter
import com.grocery.sainik_grocery.utils.Shared_Preferences
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel

class ShopFromActivity : BaseActivity(), ChooseMainCategoryAdapter.OnItemClickListener {

    lateinit var binding: ActivityShopFromBinding
    private lateinit var viewModel: CommonViewModel
    private var chooseMainCategoryAdapter:ChooseMainCategoryAdapter?=null

    override fun resourceLayout(): Int {
        return R.layout.activity_shop_from
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityShopFromBinding
    }

    override fun setFunction() {
        with(binding){
            val vm: CommonViewModel by viewModels {
                CommonModelFactory(ApiHelper(ApiClient.apiService))
            }

            viewModel = vm

            tvCurrentaddress.text = LocationActivity.locationAddress

            chooseMainCategoryAdapter = ChooseMainCategoryAdapter(this@ShopFromActivity, this@ShopFromActivity)
            rvChoosefavourites.adapter = chooseMainCategoryAdapter
            rvChoosefavourites.layoutManager = GridLayoutManager(this@ShopFromActivity, 1)

            ivBack.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                onBackPressedDispatcher.onBackPressed()
            }

//            btnCsd.setOnClickListener {
//                Toast.makeText(this@ShopFromActivity, "Coming Soon", Toast.LENGTH_SHORT).show()
//            }

            btnNonCSD.setOnClickListener {
//                val intent = Intent(this@ShopFromActivity, URCListActivity::class.java)
//                startActivity(intent)



            }
//            btnCsd.setOnClickListener {
//                val intent = Intent(this@ShopFromActivity, MainActivity::class.java)
//                startActivity(intent)
//            }
//
//            btnNonCSD.setOnClickListener {
//                val intent = Intent(this@ShopFromActivity, MainActivity::class.java)
//                startActivity(intent)
//            }
        }

        mainCategotylist()

    }


    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }


    private fun mainCategotylist(){

        if (Utilities.isNetworkAvailable(this)) {
            viewModel.ProductMainCategoriesList()
                .observe(this) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                hideProgressDialog()
                                resource.data?.let { itResponse ->

                                    if (itResponse.status) {

                                        chooseMainCategoryAdapter?.updateData(itResponse.data)

                                    } else {

                                        Toast.makeText(
                                            this,
                                            resource.data?.message,
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                }


                            }

                            Status.ERROR -> {
                                hideProgressDialog()
                                val builder = AlertDialog.Builder(this)
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
                               showProgressDialog()
                            }

                        }

                    }
                }

        } else {
            Toast.makeText(this, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT)
                .show()
        }
    }


    fun showProgressDialog() {
        binding.rlLoading.visibility = View.VISIBLE
    }

    fun hideProgressDialog() {
        binding.rlLoading.visibility = View.GONE

    }

    override fun onClick(position: Int, view: View, mMaincategotyModelArrayList: ArrayList<Data>) {

        Shared_Preferences.setMaincatid(mMaincategotyModelArrayList[position].id)
        val intent = Intent(this@ShopFromActivity, MainActivity::class.java)
        startActivity(intent)
    }

}