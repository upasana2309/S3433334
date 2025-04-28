package com.example.eventreminderapp.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val api: HolidayApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://date.nager.at/api/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HolidayApi::class.java)
    }
}
