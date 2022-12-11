package com.example.patientcareapplication

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageApiService {
    @GET
    suspend fun getPhoto(@Query("client_id")key: String,
                         @Query("count") count : Int

    ): String
}
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl("https://api.unsplash.com/")
    .build()

object ImageApi{
val retrofitService: ImageApiService by lazy{
    retrofit.create(ImageApiService::class.java)
}
}