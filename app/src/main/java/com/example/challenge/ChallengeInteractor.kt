package com.example.challenge

import com.example.challengelibrary.PaintMaker
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.InputStream

class ChallengeInteractor(val presenter: ChallengePresenter) {

    fun fetchFileFromServer(url: String) {
        presenter.showProgress(true)
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
                    presenter.showProgress(false)
                    response.body()?.byteStream()?.let {
                        processStringData(it)
                        return
                    }

                    presenter.showError("")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                presenter.showProgress(false)
                presenter.showError(t.toString())
            }

        })
    }

    fun processStringData(inputStream: InputStream) {
        val paintMaker = PaintMaker()
        try {
            val result = paintMaker.processOrderInput(inputStream)
            presenter.showResult(result)
        } catch (numberException: NumberFormatException) {
            presenter.showError("Invalid Input")
        }
    }

}