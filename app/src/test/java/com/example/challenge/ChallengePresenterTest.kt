package com.example.challenge

import com.nhaarman.mockitokotlin2.anyOrNull
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations


class ChallengePresenterTest {

    @Mock
    lateinit var mockView: ChallengeView

    @Mock
    lateinit var mockCallback: ChallengeInteractorCallback

    lateinit var presenter: ChallengePresenter

    val stubTestCase = "2\n" +
            "5\n" +
            "3\n" +
            "1 1 1\n" +
            "2 1 0 2 0\n" +
            "1 5 0\n" +
            "1\n" +
            "2\n" +
            "1 1 0\n" +
            "1 1 1"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = ChallengePresenter(mockView)
        val mockInterator = ChallengeInteractor(mockCallback)
        presenter.interactor = mockInterator
    }

    @After
    fun tearDown() {
        Mockito.reset(mockView)
        Mockito.reset(mockCallback)
    }

    @Test
    fun testShowProgress_ViewShowLoading() {
        presenter.showProgress(true)
        verify(mockView).showProgress(true)
        presenter.showProgress(false)
        verify(mockView).showProgress(false)
    }

    @Test
    fun showResult_withNonEmptyResult_displayresult() {
        val stubResult = arrayOf<String>("1", "2")
        presenter.showResult(stubResult)
        verify(mockView).showResult(stubResult)
    }

    @Test
    fun showResult_withEmptyResult_notDisplayResult() {
        val stubResult = arrayOf<String>()
        presenter.showResult(stubResult)
        verify(mockView, never()).showResult(stubResult)
    }

    @Test
    fun showError_withErrorString_displayError() {
        val stubString = "Error"
        presenter.showError(stubString)
        verify(mockView).showError(stubString)
    }

    @Test
    fun fetchString_showLoading() {
        val stubUrl = "www.zalando.fi"
        presenter.fetchString(stubUrl)
        verify(mockCallback).showProgress(true)
    }

    @Test
    fun fetchEmptyString_showError() {
        val stubUrl = ""
        presenter.fetchString(stubUrl)
        verify(mockView).showError("Invalid Input")
    }

    @Test
    fun solveStringInput_withInvalidInput_fail() {
        presenter.solveStringInput("zalando")
        verify(mockCallback).onFailure("Invalid Input")
    }

    @Test
    fun solveStringInput_withValidInput_success() {
        presenter.solveStringInput(
            stubTestCase
        )

        verify(mockCallback).onSuccess(anyOrNull())
    }

    @Test
    fun onfailure_showError() {
        presenter.onFailure("")
        verify(mockView).showError("")
    }

    @Test
    fun onSuccess_showResult() {
        presenter.onSuccess(arrayOf("1"))
        verify(mockView).showResult(anyOrNull())
    }

}