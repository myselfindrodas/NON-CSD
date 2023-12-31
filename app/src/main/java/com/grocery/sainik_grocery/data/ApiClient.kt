package com.grocery.sainik_grocery.data

import com.grocery.sainik_grocery.utils.Shared_Preferences
import com.grocery.sainik_grocery.data.ApiInterface
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
//    const val BASE_URL = "https://maitricomplex.in/api/"  //Dev
//    const val BASE_URL = "https://maitricomplex.in/api/"  //Dev
    const val BASE_URL = "https://maitricomplex.in/api/"  //Live
//const val BASE_URL = "http://10.200.109.64:8060/"  //Dev

    private var retrofit: Retrofit? = null
    val retrofitInstance: Retrofit?
        get() {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }


    private fun getRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()
        //Timeout
        httpClient.readTimeout(ApiConfig.READ_TIMEOUT, TimeUnit.SECONDS)
        httpClient.connectTimeout(ApiConfig.CONNECT_TIMEOUT, TimeUnit.SECONDS)
        httpClient.writeTimeout(ApiConfig.WRITE_TIMEOUT, TimeUnit.SECONDS)


        val interceptorbody = HttpLoggingInterceptor()
        interceptorbody.setLevel(HttpLoggingInterceptor.Level.BODY)

        val interceptor =
            Interceptor { chain ->
                val request = chain.request()
                    .newBuilder()
//                    .addHeader("Authorization", "Bearer " + "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJEQl9TVEFUSVNUSUNTIjoidHJ1ZSIsIkRCX0JFU1RfU0VMTElOR19QUk9TIjoidHJ1ZSIsIkRCX1JFTUlOREVSUyI6InRydWUiLCJEQl9MQVRFU1RfSU5RVUlSSUVTIjoidHJ1ZSIsIkRCX1JFQ0VOVF9TT19TSElQTUVOVCI6InRydWUiLCJEQl9SRUNFTlRfUE9fREVMSVZFUlkiOiJ0cnVlIiwiUFJPX1ZJRVdfUFJPRFVDVFMiOiJ0cnVlIiwiUFJPX0FERF9QUk9EVUNUIjoidHJ1ZSIsIlBST19VUERBVEVfUFJPRFVDVCI6InRydWUiLCJQUk9fREVMRVRFX1BST0RVQ1QiOiJ0cnVlIiwiUFJPX01BTkFHRV9QUk9fQ0FUIjoidHJ1ZSIsIlBST19NQU5BR0VfVEFYIjoidHJ1ZSIsIlBST19NQU5BR0VfVU5JVCI6InRydWUiLCJQUk9fTUFOQUdFX0JSQU5EIjoidHJ1ZSIsIlBST19NQU5BR0VfV0FSRUhPVVNFIjoidHJ1ZSIsIlNVUFBfVklFV19TVVBQTElFUlMiOiJ0cnVlIiwiU1VQUF9BRERfU1VQUExJRVIiOiJ0cnVlIiwiU1VQUF9VUERBVEVfU1VQUExJRVIiOiJ0cnVlIiwiU1VQUF9ERUxFVEVfU1VQUExJRVIiOiJ0cnVlIiwiQ1VTVF9WSUVXX0NVU1RPTUVSUyI6InRydWUiLCJDVVNUX0FERF9DVVNUT01FUiI6InRydWUiLCJDVVNUX1VQREFURV9DVVNUT01FUiI6InRydWUiLCJDVVNUX0RFTEVURV9DVVNUT01FUiI6InRydWUiLCJJTlFfVklFV19JTlFVSVJJRVMiOiJ0cnVlIiwiSU5RX0FERF9JTlFVSVJZIjoidHJ1ZSIsIklOUV9VUERBVEVfSU5RVUlSWSI6InRydWUiLCJJTlFfREVMRVRFX0lOUVVJUlkiOiJ0cnVlIiwiSU5RX01BTkFHRV9JTlFfU1RBVFVTIjoidHJ1ZSIsIklOUV9NQU5BR0VfSU5RX1NPVVJDRSI6InRydWUiLCJJTlFfTUFOQUdFX1JFTUlOREVSUyI6InRydWUiLCJQT1JfVklFV19QT19SRVFVRVNUUyI6InRydWUiLCJQT1JfQUREX1BPX1JFUVVFU1QiOiJ0cnVlIiwiUE9SX1VQREFURV9QT19SRVFVRVNUIjoidHJ1ZSIsIlBPUl9ERUxFVEVfUE9fUkVRVUVTVCI6InRydWUiLCJQT1JfQ09OVkVSVF9UT19QTyI6InRydWUiLCJQT1JfR0VORVJBVEVfSU5WT0lDRSI6InRydWUiLCJQT1JfUE9SX0RFVEFJTCI6InRydWUiLCJQT19WSUVXX1BVUkNIQVNFX09SREVSUyI6InRydWUiLCJQT19BRERfUE8iOiJ0cnVlIiwiUE9fVVBEQVRFX1BPIjoidHJ1ZSIsIlBPX0RFTEVURV9QTyI6InRydWUiLCJQT19WSUVXX1BPX0RFVEFJTCI6InRydWUiLCJQT19SRVRVUk5fUE8iOiJ0cnVlIiwiUE9fVklFV19QT19QQVlNRU5UUyI6InRydWUiLCJQT19BRERfUE9fUEFZTUVOVCI6InRydWUiLCJQT19ERUxFVEVfUE9fUEFZTUVOVCI6InRydWUiLCJQT19HRU5FUkFURV9JTlZPSUNFIjoidHJ1ZSIsIlNPX1ZJRVdfU0FMRVNfT1JERVJTIjoidHJ1ZSIsIlNPX0FERF9TTyI6InRydWUiLCJTT19VUERBVEVfU08iOiJ0cnVlIiwiU09fREVMRVRFX1NPIjoidHJ1ZSIsIlNPX1ZJRVdfU09fREVUQUlMIjoidHJ1ZSIsIlNPX1JFVFVSTl9TTyI6InRydWUiLCJTT19WSUVXX1NPX1BBWU1FTlRTIjoidHJ1ZSIsIlNPX0FERF9TT19QQVlNRU5UIjoidHJ1ZSIsIlNPX0RFTEVURV9TT19QQVlNRU5UIjoidHJ1ZSIsIlNPX0dFTkVSQVRFX0lOVk9JQ0UiOiJ0cnVlIiwiSU5WRV9WSUVXX0lOVkVOVE9SSUVTIjoidHJ1ZSIsIklOVkVfTUFOQUdFX0lOVkVOVE9SWSI6InRydWUiLCJFWFBfVklFV19FWFBFTlNFUyI6InRydWUiLCJFWFBfQUREX0VYUEVOU0UiOiJ0cnVlIiwiRVhQX1VQREFURV9FWFBFTlNFIjoidHJ1ZSIsIkVYUF9ERUxFVEVfRVhQRU5TRSI6InRydWUiLCJFWFBfTUFOQUdFX0VYUF9DQVRFR09SWSI6InRydWUiLCJSRVBfUE9fUkVQIjoidHJ1ZSIsIlJFUF9TT19SRVAiOiJ0cnVlIiwiUkVQX1BPX1BBWU1FTlRfUkVQIjoidHJ1ZSIsIlJFUF9FWFBFTlNFX1JFUCI6InRydWUiLCJSRVBfU09fUEFZTUVOVF9SRVAiOiJ0cnVlIiwiUkVQX1NBTEVTX1ZTX1BVUkNIQVNFX1JFUCI6InRydWUiLCJSRU1fVklFV19SRU1JTkRFUlMiOiJ0cnVlIiwiUkVNX0FERF9SRU1JTkRFUiI6InRydWUiLCJSRU1fVVBEQVRFX1JFTUlOREVSIjoidHJ1ZSIsIlJFTV9ERUxFVEVfUkVNSU5ERVIiOiJ0cnVlIiwiUk9MRVNfVklFV19ST0xFUyI6InRydWUiLCJST0xFU19BRERfUk9MRSI6InRydWUiLCJST0xFU19VUERBVEVfUk9MRSI6InRydWUiLCJST0xFU19ERUxFVEVfUk9MRSI6InRydWUiLCJVU1JfVklFV19VU0VSUyI6InRydWUiLCJVU1JfQUREX1VTRVIiOiJ0cnVlIiwiVVNSX1VQREFURV9VU0VSIjoidHJ1ZSIsIlVTUl9ERUxFVEVfVVNFUiI6InRydWUiLCJVU1JfUkVTRVRfUFdEIjoidHJ1ZSIsIlVTUl9BU1NJR05fVVNSX1JPTEVTIjoidHJ1ZSIsIlVTUl9BU1NJR05fVVNSX1BFUk1JU1NJT05TIjoidHJ1ZSIsIlVTUl9PTkxJTkVfVVNFUlMiOiJ0cnVlIiwiRU1BSUxfTUFOQUdFX0VNQUlMX1NNVFBfU0VUVElOUyI6InRydWUiLCJFTUFJTF9NQU5BR0VfRU1BSUxfVEVNUExBVEVTIjoidHJ1ZSIsIkVNQUlMX1NFTkRfRU1BSUwiOiJ0cnVlIiwiU0VUVF9VUERBVEVfQ09NX1BST0ZJTEUiOiJ0cnVlIiwiU0VUVF9NQU5BR0VfQ09VTlRSWSI6InRydWUiLCJTRVRUX01BTkFHRV9DSVRZIjoidHJ1ZSIsIkxPR1NfVklFV19MT0dJTl9BVURJVFMiOiJ0cnVlIiwiTE9HU19WSUVXX0VSUk9SX0xPR1MiOiJ0cnVlIiwiUkVQX1BST19QUF9SRVAiOiJ0cnVlIiwiUkVQX0NVU1RfUEFZTUVOVF9SRVAiOiJ0cnVlIiwiUkVQX1BST19TT19SRVBPUlQiOiJ0cnVlIiwiUkVQX1NVUF9QQVlNRU5UX1JFUCI6InRydWUiLCJSRVBfU1RPQ0tfUkVQT1JUIjoidHJ1ZSIsIlBPU19QT1MiOiJ0cnVlIiwiUkVQX1ZJRVdfV0FSX1JFUCI6InRydWUiLCJSRVBfVklFV19QUk9fTE9TU19SRVAiOiJ0cnVlIiwic3ViIjoiNGIzNTJiMzctMzMyYS00MGM2LWFiMDUtZTM4ZmNmMTA5NzE5IiwiRW1haWwiOiJhZG1pbkBnbWFpbC5jb20iLCJuYmYiOjE2OTg5OTA2NTcsImV4cCI6MTY5OTAzMzg1NywiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo1MDAwIiwiYXVkIjoiUFRDVXNlcnMifQ.cBSdyxIPDn7c-FdrQcCodL7BpxCTfIWdY0vxaHI7nvw")
                    .addHeader("Authorization", "Bearer " + Shared_Preferences.getToken()!!)
                    .addHeader("x-requested-with", "XMLHttpRequest")
                    .build()
                // }
                return@Interceptor chain.proceed(request)
            }

        httpClient.addInterceptor(interceptor)
        httpClient.addInterceptor(interceptorbody)
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build() //Doesn't require the adapter
    }





    val apiService: ApiInterface = getRetrofit().create(ApiInterface::class.java)

}