package com.example.challenge

interface ChallengeView {

    fun showProgress(isLoading: Boolean)

    fun showResult(list: Array<String>)

    fun showError(error: String)

}