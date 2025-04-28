package com.example.eventreminderapp.network

import com.example.eventreminderapp.data.model.Holiday
import retrofit2.http.GET
import retrofit2.http.Path

interface HolidayApi {
    @GET("PublicHolidays/{year}/{countryCode}")
    suspend fun getPublicHolidays(
        @Path("year") year: Int,
        @Path("countryCode") countryCode: String
    ): List<Holiday>
}
