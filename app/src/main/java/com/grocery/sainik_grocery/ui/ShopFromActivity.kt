package com.grocery.sainik_grocery.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.base.BaseActivity
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.ActivityShopFromBinding
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel

class ShopFromActivity : BaseActivity() {

    lateinit var binding: ActivityShopFromBinding
    private lateinit var viewModel: CommonViewModel

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

            ivBack.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                onBackPressedDispatcher.onBackPressed()
            }

            btnCsd.setOnClickListener {
                Toast.makeText(this@ShopFromActivity, "Coming Soon", Toast.LENGTH_SHORT).show()
            }

            btnNonCSD.setOnClickListener {
                val intent = Intent(this@ShopFromActivity, URCListActivity::class.java)
                startActivity(intent)
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
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

}