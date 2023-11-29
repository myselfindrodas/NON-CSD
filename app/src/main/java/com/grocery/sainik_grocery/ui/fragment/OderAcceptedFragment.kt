package com.grocery.sainik_grocery.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.databinding.FragmentOderAcceptedBinding
import com.grocery.sainik_grocery.ui.MainActivity

class OderAcceptedFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var fragmentOderAcceptedBinding: FragmentOderAcceptedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()

        mainActivity.setBottomNavigationVisibility(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentOderAcceptedBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_oder_accepted, container, false)
        val root = fragmentOderAcceptedBinding.root
        mainActivity = activity as MainActivity
        fragmentOderAcceptedBinding.topnav.tvNavtitle.text = ""


        fragmentOderAcceptedBinding.topnav.clCart.setOnClickListener {

            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_cart)


        }

        if (HomeFragment.cartCount>0) {
            fragmentOderAcceptedBinding.topnav.tvCartCount.text = HomeFragment.cartCount.toString()
            fragmentOderAcceptedBinding.topnav.cvCartCount.visibility=View.VISIBLE
        }else{
            fragmentOderAcceptedBinding.topnav.cvCartCount.visibility=View.GONE
        }
        fragmentOderAcceptedBinding.topnav.ivNotification.setOnClickListener {

            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_notification)


        }
        fragmentOderAcceptedBinding.topnav.btnBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()
        }


        fragmentOderAcceptedBinding.topnav.btnWishlist.setOnClickListener {

            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_wishlist)

        }

        return root
    }

}