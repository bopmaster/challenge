package com.example.challenge

import java.io.ByteArrayInputStream
import java.io.InputStream

class ChallengePresenter(val view: ChallengeView) : ChallengeInteractorCallback {

    override fun onSuccess(result: Array<String>) {
        view.showResult(result)
    }

    override fun onFailure(message: String) {
        view.showError(message)
    }

    var interactor = ChallengeInteractor(this)

    override fun showProgress(isLoading: Boolean) {
        view.showProgress(isLoading)
    }

    fun showResult(list: Array<String>) {
        if (list.isEmpty()) {
            view.showError("Invalid Input")
        } else {
            view.showResult(list)
        }
    }

    fun showError(error: String) {
        view.showError(error)
    }

    fun fetchString(url: String) {
        if (url.isNotEmpty()) {
            interactor.fetchFileFromServer(url)
        } else {
            showError("Invalid Input")
        }
    }

    fun solveStringInput(input: String) {
        val stream = ByteArrayInputStream(input.toByteArray(Charsets.UTF_8))
        interactor.processStringData(stream)
    }

    fun solveFileInput(inputStream: InputStream) {
        interactor.processStringData(inputStream)
    }
}