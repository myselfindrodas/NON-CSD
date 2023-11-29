package com.grocery.sainik_grocery.base

import com.grocery.sainik_grocery.data.ApiInterface
import com.shyamfuture.tantujayarnbank.data.model.ErrorResponse
import com.grocery.sainik_grocery.utils.NetworkConstants
import com.grocery.sainik_grocery.utils.Shared_Preferences
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


abstract class BaseApi {
    companion object {
        private const val TAG = "BaseApi"

        private var retrofit: Retrofit?=null
        fun getRetrofitInstance(): Retrofit? {

            val interceptor = if (Shared_Preferences.getToken()!!.isNotEmpty()) {
                Interceptor { chain ->
                    val request = chain.request()
                        .newBuilder()
                        .addHeader("Authorization", "Bearer " + Shared_Preferences.getToken()!!)
                        .addHeader("x-requested-with", "XMLHttpRequest")
                        .build()
                    // }
                    return@Interceptor chain.proceed(request)
                }
            } else {
                Interceptor { chain ->
                    val request = chain.request()
                        .newBuilder()
                        //.addHeader("Authorization", "Token " + Shared_Preferences.getToken()!!)
                        .addHeader("x-requested-with", "XMLHttpRequest")
                        .build()
                    // }
                    return@Interceptor chain.proceed(request)
                }
            }
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
//
            val okHttpClient = OkHttpClient.Builder()
                .readTimeout(5000, TimeUnit.SECONDS)
                .connectTimeout(3000, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(loggingInterceptor)
                .build()

            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(NetworkConstants.LOCAL_BASE_URL1)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }

    private var retrofit: Retrofit
    var service: ApiInterface

    init {

        val interceptor = if (Shared_Preferences.getToken()!!.isNotEmpty()) {
            Interceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer " + Shared_Preferences.getToken()!!)
                    .build()
                // }
                return@Interceptor chain.proceed(request)
            }
        } else {
            Interceptor { chain ->
                val request = chain.request()
                    .newBuilder()
                    //.addHeader("Authorization", "Token " + Shared_Preferences.getToken()!!)
                    .addHeader("x-requested-with", "XMLHttpRequest")
                    .build()
                // }
                return@Interceptor chain.proceed(request)
            }
        }
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }
//
        val okHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.SECONDS)
            .connectTimeout(3000, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(NetworkConstants.LOCAL_BASE_URL1)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()



        service = retrofit.create<ApiInterface>(
            ApiInterface::class.java
        )

    }


    fun errorCodeMessage(responseCode: Int, message: String = ""): ErrorResponse {
        return when (responseCode) {
            401 -> ErrorResponse(401, NetworkConstants.API_AUTHENTICATION_FAILED)
            400 -> ErrorResponse(400, NetworkConstants.API_BAD_REQUEST)
            404 -> ErrorResponse(404, NetworkConstants.API_TRY_AGAIN)
            403 -> ErrorResponse(403, NetworkConstants.TOKEN_INVALID)
            500 -> ErrorResponse(500, NetworkConstants.API_SERVER_ERROR)
            503 -> ErrorResponse(503, NetworkConstants.API_SERVER_MAINTANANCE)
            else -> ErrorResponse(responseCode, message)
        }
    }
}