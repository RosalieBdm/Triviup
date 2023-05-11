package com.example.triviup.network

import com.example.triviup.utils.Constants
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

/**
 * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
 * full Kotlin compatibility.
 */
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


/**
 * Use the Retrofit builder to build a retrofit object using a Moshi converter with our Moshi
 * object.
 */

private val factListRetrofit = Retrofit.Builder()
    .client(
        OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()
    )
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(Constants.NINJA_BASE_URL)
    .build()

interface NinjaApiService {
    @GET("v1/facts")
    suspend fun getFacts(
        @Header("X-Api-Key") API_KEY: String = Constants.NINJA_API_KEY,
        @Query("limit") limit: Int = 1,
    ): List<FactResponse>
}

object NinjaApi {
    val factRetrofitService: NinjaApiService by lazy { factListRetrofit.create(NinjaApiService::class.java)}
}