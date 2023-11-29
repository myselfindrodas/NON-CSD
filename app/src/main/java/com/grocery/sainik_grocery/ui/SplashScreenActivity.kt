package com.grocery.sainik_grocery.ui

import android.content.Intent
import android.os.Handler
import android.os.Looper
import androidx.databinding.ViewDataBinding
import com.grocery.sainik_grocery.R
import com.grocery.sainik_grocery.base.BaseActivity
import com.grocery.sainik_grocery.databinding.ActivitySplashBinding
import com.grocery.sainik_grocery.utils.AnimUtils
import com.grocery.sainik_grocery.utils.Shared_Preferences

class SplashScreenActivity : BaseActivity() {

    lateinit var binding: ActivitySplashBinding

    override fun resourceLayout(): Int {
        return R.layout.activity_splash
    }

    override fun initializeBinding(binding: ViewDataBinding) {
        this.binding = binding as ActivitySplashBinding
    }

    override fun setFunction() {

        callNextScreen()
    }

    private fun callNextScreen() {
        Handler(Looper.myLooper()!!).postDelayed({


            if (Shared_Preferences.getLoginStatus()==true){

                val intent = Intent(this, LocationActivity::class.java)
                startActivity(intent)
                AnimUtils.FadeOutAnim(this)
                finish()

            }else{

                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                AnimUtils.FadeOutAnim(this)
                finish()
            }


        }, 3000)
    }
}