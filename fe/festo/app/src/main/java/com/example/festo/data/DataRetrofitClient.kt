package com.example.festo.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataRetrofitClient {
    private const val BASE_URL = "https://k8c106.p.ssafy.io/data/" // 서버도메인URL
    private var dataRetrofit: Retrofit? = null
    open val client: Retrofit?
        get() {
            if (dataRetrofit == null) {
                dataRetrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL) // 접속할 서버를 설정
                    .addConverterFactory(GsonConverterFactory.create()) // json response를 파싱하기 위해 Gson을 설정
                    .build()
            }
            return dataRetrofit
        }
}