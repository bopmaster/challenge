package com.example.challenge

interface ChallengeView {

    fun showProgress()

    fun showResult(list: Array<String>)

    fun showError(error: String)

}