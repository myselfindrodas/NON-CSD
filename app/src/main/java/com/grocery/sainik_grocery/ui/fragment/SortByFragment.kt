package com.grocery.sainik_grocery.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.databinding.FragmentSortByBinding
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.utils.Utilities


class SortByFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var fragmentSortByBinding: FragmentSortByBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSortByBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_sort_by, container, false)
        val root = fragmentSortByBinding.root
        mainActivity = activity as MainActivity

        return root
    }

    override fun onResume() {
        super.onResume()

        mainActivity.setBottomNavigationVisibility(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentSortByBinding.topnav.tvNavtitle.text = "Sort by"

        with(fragmentSortByBinding){
            llPopularity.setOnClickListener {
                clearRadioButton(rbPopularity)

            }
            llAlphabete.setOnClickListener {
                clearRadioButton(rbAlphabete)

            }
            llHighLow.setOnClickListener {
                clearRadioButton(rbHighLow)

            }
            llLowHigh.setOnClickListener {
                clearRadioButton(rbLowHigh)

            }

            if (HomeFragment.cartCount>0) {
                topnav.tvCartCount.text = HomeFragment.cartCount.toString()
                topnav.cvCartCount.visibility=View.VISIBLE
            }else{
                topnav.cvCartCount.visibility=View.GONE
            }
            when(ProductListFragment.sortType){
                "alphabate"->{
                    clearRadioButton(rbAlphabete)
                }
                "lowtohigh"->{
                    clearRadioButton(rbLowHigh)

                }
                "hightolow"->{
                    clearRadioButton(rbHighLow)

                }
                "popularity"->{
                    clearRadioButton(rbPopularity)

                }
            }
            btnApply.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                val navController = findNavController()
                when{
                    rbAlphabete.isChecked->{
                        navController.previousBackStackEntry?.savedStateHandle?.set("sort_type", "alphabate")

                        ProductListFragment.sortType = "alphabate"
                    }
                    rbLowHigh.isChecked->{
                        navController.previousBackStackEntry?.savedStateHandle?.set("sort_type", "lowtohigh")

                        ProductListFragment.sortType = "lowtohigh"
                    }
                    rbHighLow.isChecked->{
                        navController.previousBackStackEntry?.savedStateHandle?.set("sort_type", "hightolow")
                        ProductListFragment.sortType = "hightolow"
                    }
                    rbPopularity.isChecked->{
                        navController.previousBackStackEntry?.savedStateHandle?.set("sort_type", "popularity")
                        ProductListFragment.sortType = "popularity"

                    }
                }
                navController.popBackStack()
            }
            btnCancel.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                mainActivity.onBackPressedDispatcher.onBackPressed()
            }
            topnav.btnBack.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                mainActivity.onBackPressedDispatcher.onBackPressed()
            }

            topnav.ivNotification.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_notification)


            }
            topnav.clCart.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_cart)


            }

            topnav.btnWishlist.setOnClickListener {
                if (Utilities.isClickRecently()){
                    return@setOnClickListener
                }
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_wishlist)

            }
        }
    }

    private fun clearRadioButton(radioButton: RadioButton){
        with(fragmentSortByBinding){
            rbAlphabete.isChecked=false
            rbHighLow.isChecked=false
            rbLowHigh.isChecked=false
            rbPopularity.isChecked=false
        }
        radioButton.isChecked=true
    }
}