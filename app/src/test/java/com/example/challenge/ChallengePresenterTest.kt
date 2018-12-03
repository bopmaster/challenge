package com.example.challenge

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.mockito.Spy

class ChallengePresenterTest {

    @Mock
    lateinit var mockView: ChallengeView

    @Spy
    var mockInteractor = mock(ChallengeInteractorImpl::class.java)

    lateinit var presenter: ChallengePresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = ChallengePresenter(mockView)
        presenter.interactor = mockInteractor
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
    fun fetchString() {
        val stubUrl = "www.zalando.fi"
        presenter.fetchString(stubUrl)
        verify(mockInteractor).fetchFileFromServer(stubUrl)
    }

    @Test
    fun solveStringInput() {
    }

    @Test
    fun solveFileInput() {
    }

    @Test
    fun getView() {
    }
}