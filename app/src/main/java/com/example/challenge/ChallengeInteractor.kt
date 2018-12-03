package com.example.challenge

import com.example.challengelibrary.PaintMaker
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.InputStream

interface ChallengeInteractorCallback {
    fun onSuccess(result: Array<String>)
    fun onFailure(message: String)
    fun showProgress(isLoading: Boolean)
}

class ChallengeInteractor(val callback: ChallengeInteractorCallback) {

    fun fetchFileFromServer(url: String) {
        callback.showProgress(true)
        val retrofit = Retrofit
                .Builder()
                .baseUrl("https://www.zalando.fi")
                .client(OkHttpClient.Builder().build())
                .build()
        val fileService = retrofit.create(FileApi::class.java)
        val call = fileService.fetchFileFromUrl(url)

        call.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                callback.showProgress(false)
                if (response.isSuccessful) {
                    // Process data
                    response.body()?.byteStream()?.let {
                        processStringData(it)
                        return
                    }
                    callback.onFailure("Unexpected Error")
                } else {
                    callback.onFailure("Invalid Link")
                }
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                callback.showProgress(false)
                callback.onFailure(t.toString())
            }

        })
    }

    fun processStringData(inputStream: InputStream) {
        val paintMaker = PaintMaker()
        try {
            val result = paintMaker.processOrderInput(inputStream)
            callback.onSuccess(result)
        } catch (numberException: NumberFormatException) {
            callback.onFailure("Invalid String Input")
        }
    }

}