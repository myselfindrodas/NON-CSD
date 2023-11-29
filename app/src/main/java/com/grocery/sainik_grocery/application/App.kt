package com.grocery.sainik_grocery.application

import android.app.Application
import android.content.Context
import android.util.Log


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.e("Application", "Application Started")
        instance = this

        appContext = applicationContext

      //  Picasso.setSingletonInstance(Picasso.Builder(this).build())

    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

    init {
        instance = this
    }


    companion object {
        var appContext: Context? = null

        private var instance: App? = null

        fun getInstance(): App {
            return instance as App
        }

    }

}