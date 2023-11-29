package com.grocery.sainik_grocery.base

import android.content.Context
import android.content.ContextWrapper
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.window.OnBackInvokedDispatcher
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.grocery.sainik_grocery.utils.AnimUtils
import com.grocery.sainik_grocery.utils.ContextUtils
import com.grocery.sainik_grocery.utils.Shared_Preferences
import java.util.*


/**Base class for all activity*/
abstract class BaseActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "BaseActivity"
        const val FRAGMENT_TAG_LOAD = "LOAD"

    }
    /**Define the resource xml layout*/
    protected abstract fun resourceLayout(): Int

    /**Define all binding classes*/
    protected abstract fun initializeBinding(binding: ViewDataBinding)

    /**initialize and define all function and logics here*/

    protected abstract fun setFunction()


    override fun attachBaseContext(newBase: Context) {
// get chosen language from shread preference
        val localeToSwitchTo = Locale(Shared_Preferences.getLangStatus())
        val localeUpdatedContext: ContextWrapper = ContextUtils.updateLocale(newBase, localeToSwitchTo)
        super.attachBaseContext(localeUpdatedContext)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }*/
        hideKeyboard()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        val binding: ViewDataBinding = DataBindingUtil
            .setContentView(this, resourceLayout())


        initializeBinding(binding)

        setFunction()

       /* if (Build.VERSION.SDK_INT >= 33) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(
                OnBackInvokedDispatcher.PRIORITY_DEFAULT
            ) {

                onBackPressedDispatcher.onBackPressed()
               // exitOnBackPressed()
            }
        } else {
            onBackPressedDispatcher.addCallback(
                this,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {

                        Log.i("TAG", "handleOnBackPressed: Exit")
                        onBackPressedDispatcher.onBackPressed()
                       // exitOnBackPressed()
                    }
                })
        }*/

    }
/**Hide keyboard*/
    fun hideKeyboard() {
        val view = currentFocus
        if (view != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
            imm?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
    /*fun isNetworkConnected(): Boolean {
        return CommonUtility.isNetworkAvailable(applicationContext) && CommonUtility.isNetworkConnected(applicationContext)
    }*/


    override fun onBackPressed() {
        hideKeyboard()
        AnimUtils.FadeInAnim(this@BaseActivity)

        onBackPressedDispatcher.onBackPressed()

    }


/*
    open fun popBackStackInclusive(activity: AppCompatActivity) {
        val fragmentManager: FragmentManager = activity.supportFragmentManager
        for (i in 1 until fragmentManager.backStackEntryCount) {
            try {
                val fragmentId: Int = fragmentManager.getBackStackEntryAt(i).getId()
                fragmentManager.popBackStack(fragmentId, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            } catch (e: Exception) {
                Log.d("Fragment Back", e.localizedMessage.toString())
            }
        }
    }*/

}