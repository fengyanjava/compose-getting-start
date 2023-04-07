package com.helijia.merchant.compose.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by 星空 on 2023/4/7 14:02
 * Email: xingkong@helijia.com
 */

object ServiceCreator {

    private const val baseUrl = "https://p.helijia.com"

    private fun getRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun <T> create(serviceClass: Class<T>): T = getRetrofit().create(serviceClass)

    inline fun <reified T> create(): T = create(T::class.java)
}