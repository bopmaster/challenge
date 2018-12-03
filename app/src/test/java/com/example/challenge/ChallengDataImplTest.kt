package com.example.challenge

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ChallengDataImplTest {

    @Mock
    lateinit var mockView: ChallengeView

    lateinit var presenter: ChallengePresenter

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        presenter = ChallengePresenter(mockView)
    }

    @Test
    fun fetchFileFromServer() {

    }

    @Test
    fun processStringData() {

    }

}