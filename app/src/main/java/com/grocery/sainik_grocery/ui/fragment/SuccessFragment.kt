package com.grocery.sainik_grocery.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.FragmentSuccessBinding
import com.grocery.sainik_grocery.ui.LocationActivity
import com.grocery.sainik_grocery.ui.MainActivity
import com.grocery.sainik_grocery.viewmodel.CommonViewModel

class SuccessFragment : Fragment() {

    lateinit var binding: FragmentSuccessBinding
    lateinit var mainActivity: MainActivity
    var total=""
    var address = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_success, container, false)
        val root = binding.root
        mainActivity = activity as MainActivity

        val intent = arguments
        if (intent != null && intent.containsKey("address")) {
            address = intent.getString("address", "")
        }

        if (intent != null && intent.containsKey("amount")) {
            total = intent.getString("amount", "")
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.topnav.btnBack.visibility = View.GONE
        binding.topnav.tvNavtitle.text = "Order Placed"

        binding.tvTotalPrice.text = "₹ "+total

        binding.tvAddress.text = address

        binding.btnOrders.setOnClickListener {

            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_myorder)
        }
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        val callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {

                    val intent = Intent(mainActivity, MainActivity::class.java)
                    startActivity(intent)
                    mainActivity.finish()
//                    mainActivity.finishAffinity()
//                    if (activity != null) {
//                        activity?.finish()
//                    }

                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            callback
        )
    }

}