package com.grocery.sainik_grocery.ui

import android.app.AlertDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.ViewDataBinding
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.base.BaseActivity
import com.grocery.sainik_grocery.data.ApiClient
import com.grocery.sainik_grocery.data.ApiHelper
import com.grocery.sainik_grocery.data.model.otpverifymodel.OtpverifyRequest
import com.grocery.sainik_grocery.data.modelfactory.CommonModelFactory
import com.grocery.sainik_grocery.databinding.ActivityLoginBinding
import com.grocery.sainik_grocery.databinding.ActivityOtpBinding
import com.grocery.sainik_grocery.utils.Shared_Preferences
import com.grocery.sainik_grocery.utils.Status
import com.grocery.sainik_grocery.utils.Utilities
import com.grocery.sainik_grocery.viewmodel.CommonViewModel

class OtpActivity : BaseActivity() {

    lateinit var binding: ActivityOtpBinding
    private lateinit var viewModel: CommonViewModel
    private lateinit var otpInputBoxes: Array<EditText>
    private lateinit var timer: CountDownTimer
    var initialTime: Int = 0
    var validotp: String? = ""
    var phone: String? = ""
    var otp: String? = ""

    override fun resourceLayout(): Int {
        return R.layout.activity_otp
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivityOtpBinding
    }

    override fun setFunction() {

        val intent = intent
        validotp = intent.getStringExtra("otp")
        phone = intent.getStringExtra("phone")

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm



//        initialTime = 1 * 60
//        startTimer(initialTime)
//
////        binding.btnVerify.setOnClickListener {
////            if (binding.otp1.text.toString().isEmpty() || binding.otp2.text.toString().isEmpty() || binding.otp3.text.toString().isEmpty() || binding.otp4.text.toString().isEmpty()){
////                Toast.makeText(this, "Enter OTP", Toast.LENGTH_SHORT).show()
////            } else{
////                startActivity(Intent(this@OtpActivity,LocationActivity::class.java))
////
////            }
////        }
//
//        binding.tvResendOtp.setOnClickListener {
//            if (binding.tvResendOtp.isEnabled) {
//                startTimer(initialTime)
//            }
//        }
//
//        otpInputBoxes = arrayOf(
//            findViewById(R.id.otp1),
//            findViewById(R.id.otp2),
//            findViewById(R.id.otp3),
//            findViewById(R.id.otp4)
//        )
//        enterOTP()

        initialTime = 1 * 60
        startTimer(initialTime)
        with(binding) {

            val index0 = 0
            val index1 = 1
            val index2 = 2
            val index3 = 3
            val otp1st: String = validotp?.get(index0).toString()
            val otp2nd: String = validotp?.get(index1).toString()
            val otp3rd: String = validotp?.get(index2).toString()
            val otp4th: String = validotp?.get(index3).toString()

            otp1.setText(otp1st)
            otp2.setText(otp2nd)
            otp3.setText(otp3rd)
            otp4.setText(otp4th)



            val otptext = ArrayList<EditText>()
            otptext.add(otp1)
            otptext.add(otp2)
            otptext.add(otp3)
            otptext.add(otp4)
            setOtpEditTextHandler(otptext)
//            clDeliveryOtp.visibility=View.VISIBLE
//            clDeliverySuccess.visibility=View.GONE
            binding.btnVerify.setOnClickListener {
                otp = otp1.text.toString() +
                        otp2.text.toString() +
                        otp3.text.toString() +
                        otp4.text.toString()

                if (otp!!.length > 3) {

                    verifyOtp()
                } else {
                    Toast.makeText(this@OtpActivity, "Enter valid OTP", Toast.LENGTH_SHORT).show()
                }
            }


            tvResendOtp.setOnClickListener {
                binding.otp1.setText("")
                binding.otp2.setText("")
                binding.otp3.setText("")
                binding.otp4.setText("")
//                resendotp()
                startTimer(initialTime)
            }
        }

    }

    private fun setOtpEditTextHandler(otpEt: java.util.ArrayList<EditText>) { //This is the function to be called
        for (i in 0..3) { //Its designed for 6 digit OTP
            otpEt.get(i).addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
                override fun afterTextChanged(s: Editable) {
                    if (i == 3 && otpEt[i].text.toString().isNotEmpty()) {

                    } else if (otpEt[i].text.toString().isNotEmpty()) {
                        otpEt[i + 1]
                            .requestFocus()
                    }
                }
            })
            otpEt[i].setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (event.action != KeyEvent.ACTION_DOWN) {
                    return@OnKeyListener false
                }
                if (keyCode == KeyEvent.KEYCODE_DEL &&
                    otpEt[i].text.toString().isEmpty() && i != 0
                ) {
                    otpEt[i - 1].setText("")
                    otpEt[i - 1].requestFocus()
                }
                false
            })
        }
    }


    private fun startTimer(initialTime: Int) {
        timer = object : CountDownTimer(initialTime * 1000L, 1000L) {
            override fun onTick(millisUntilFinished: Long) {
                binding.tvResendOtp.isEnabled = false
                binding.tvResendOtp.setTextColor(Color.parseColor("#CCCCCC"))
                binding.tvResendOtp.visibility = View.GONE
                val minutes = millisUntilFinished / 1000 / 60
                val seconds = (millisUntilFinished / 1000) % 60
                val timeLeft = String.format("%02d:%02d", minutes, seconds)
                binding.tvTimer.text = timeLeft
            }

            override fun onFinish() {
                binding.tvTimer.text = "00:00"
                binding.tvResendOtp.isEnabled = true
                binding.tvResendOtp.visibility = View.VISIBLE
                binding.tvResendOtp.setTextColor(Color.parseColor("#E8AB03"))

            }
        }.start()
    }



    private fun verifyOtp() {


        if (Utilities.isNetworkAvailable(this)) {
            viewModel.otpverify(
                OtpverifyRequest(mobileNo = Shared_Preferences.getPhoneNo(), otp = otp.toString())
            ).observe(this) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            hideProgressDialog()
                            if (resource.data?.status == true) {


                                Shared_Preferences.setLoginStatus(true)
                                Shared_Preferences.setUserId(resource.data.data.id)
                                Log.d(TAG, "userid-->"+resource.data.data.id)
                                Toast.makeText(this, resource.data.message, Toast.LENGTH_SHORT).show()
                                val intent = Intent(this@OtpActivity, LocationActivity::class.java)
                                startActivity(intent)
                                finish()

                            } else {

                                val builder = AlertDialog.Builder(this@OtpActivity)
                                builder.setMessage(it.data?.message)
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->

                                    dialog.cancel()

                                }
                                val alert = builder.create()
                                alert.setOnShowListener { arg0 ->
                                    alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(resources.getColor(R.color.blue))
                                }
                                alert.show()

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


    fun showProgressDialog() {
        binding.rlLoading.visibility = View.VISIBLE
    }

    fun hideProgressDialog() {
        binding.rlLoading.visibility = View.GONE
    }

}