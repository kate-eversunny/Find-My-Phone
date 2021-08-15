package com.androidgang.findmyphone.utils

import com.androidgang.findmyphone.models.*
import com.androidgang.findmyphone.utils.Constants.APPLICATION_JSON_TYPE
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface NetworkService {


    @GET("{email}")
    suspend fun getUser(@Path("email") email: String) : User?

    @GET("{email}/{deviceId}")
    suspend fun getUserDevice(@Path("email") email: String, @Path("deviceId")deviceId: String) : Metrics?



    @POST("{email}")
    suspend fun postUser(@Path("email") email: String, @Body device: Device)


    @POST(".")
    suspend fun postNewUser(@Body user: User)





    companion object Factory {
        fun create(): NetworkService {
            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .setClient()
                .addConverterFactory(GsonConverterFactory.create())
                .addJsonConverter()
                .build()

            return retrofit.create(NetworkService::class.java);
        }
    }

}

@Suppress("EXPERIMENTAL_API_USAGE")
fun Retrofit.Builder.addJsonConverter() = apply {
    val json = Json { ignoreUnknownKeys = true }
    val contentType = MediaType.get(APPLICATION_JSON_TYPE)
    this.addConverterFactory(json.asConverterFactory(contentType))
}


fun Retrofit.Builder.setClient() = apply {
    val okHttpClient = OkHttpClient.Builder()
        .build()

    this.client(okHttpClient)
}
