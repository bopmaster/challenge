package com.example.challengelibrary

import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.ByteArrayInputStream

class PaintMakerTest {

    lateinit var paintMaker: PaintMaker
    val stubCorrectCase = "2\n" +
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
        paintMaker = PaintMaker()
    }

    @Test
    fun correctOrder_returnCorrectResult() {
        val inputStream = ByteArrayInputStream(stubCorrectCase.toByteArray(Charsets.UTF_8))
        val wholeOrder = paintMaker.processOrderInput(inputStream)

        assertEquals(4, wholeOrder.count())
        assertEquals("Case #1: 1 0 0 0 0", wholeOrder[0])
        assertEquals("Case #2: IMPOSSIBLE", wholeOrder[1])
        assertEquals("Case #3: 0 1 0", wholeOrder[2])
        assertEquals("Case #4: 0 0 1", wholeOrder[3])
    }

    @Test
    fun incorrectOrder_returnNumberFormatException() {
        val inputStream = ByteArrayInputStream("abc".toByteArray(Charsets.UTF_8))
        try {
            paintMaker.processOrderInput(inputStream)
        } catch (exception: NumberFormatException) {
            assertTrue(true)
        }
    }


}