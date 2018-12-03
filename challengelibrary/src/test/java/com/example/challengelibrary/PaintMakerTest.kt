package com.example.challengelibrary

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.io.FileInputStream

class PaintMakerTest {

    @Test
    fun testCorrectOrderShouldReturnCorrectResult() {
        val inputStream =
            FileInputStream("/Users/thegaylord/Desktop/TGL/challenge/challengelibrary/src/test/java/com/example/challengelibrary/mockfile/test.txt")
        val wholeOrder = PaintMaker().processOrderInput(inputStream)

        assertEquals(4, wholeOrder.count())
        assertEquals("Case #1: 1 0 0 0 0", wholeOrder[0])
        assertEquals("Case #2: IMPOSSIBLE", wholeOrder[1])
        assertEquals("Case #3: 0 1 0", wholeOrder[2])
        assertEquals("Case #4: 0 0 1", wholeOrder[3])
    }


}