package com.example.challenge

class ChallengePresenter(val view: ChallengeView) {

    val interactor = ChallengeInteractor(this)

    fun showProgress() {}

    fun showResult(list: Array<String>) {}

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

    fun openStringFile(url: String) {
        interactor.fetchFileFromServer(url)
    }
}