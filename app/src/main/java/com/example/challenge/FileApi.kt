package com.example.challenge

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface FileApi {

    @GET
    fun fetchFileFromUrl(@Url url: String): Call<ResponseBody>
}