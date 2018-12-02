package com.example.challenge

import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.InputStream

class ChallengeInteractor(val presenter: ChallengePresenter) {

    fun fetchFileFromServer(url: String) {
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://www.zalando.fi")
            .client(OkHttpClient.Builder().build())
            .build()
        val fileService = retrofit.create(FileApi::class.java)
        val call = fileService.fetchFileFromUrl(url)

        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                if (response.isSuccessful) {
                    // Process data
                    response.body()?.byteStream()?.let {
                        processStringData(it)
                        return
                    }

                    presenter.showError("")

                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                presenter.showError(t.toString())
            }

        })
    }

    private fun processStringData(inputStream: InputStream) {
        presenter.showError("YOLOOOOOO")
    }

}