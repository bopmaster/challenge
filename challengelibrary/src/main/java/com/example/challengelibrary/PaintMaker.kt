package com.example.challengelibrary

import java.io.InputStream

enum class ReaderState {
    NOT_STARTED,
    START_CASE,
    READ_CUSTOMER_NUMBER,
    READ_CUSTOMER_CASE
}

class PaintMaker {

    fun processOrderInput(inputStream: InputStream): Array<String> {

        /// State when iterate through lines
        var state: ReaderState = ReaderState.NOT_STARTED

        /// Order of test has been iterated
        var testNumber = 0

        /// Number of color for each test case
        var colorCount = 0

        /// Number of customer for each test case
        var customerCount = 0

        /// Order of customer has been iterated
        var customerNumber = 0

        /// Place holder for result of each test case
        var lineResult: Array<Int> = arrayOf()

        /// Final result as a list of string
        var result: Array<String> = arrayOf()

        inputStream.bufferedReader().forEachLine {
            loop@ while (it.isNotEmpty()) {
                when (state) {
                    ReaderState.NOT_STARTED -> {
                        // First number is number of test cases
                        state = ReaderState.START_CASE
                        result = Array(it.toInt()) { "" }
                        break@loop
                    }

                    ReaderState.START_CASE -> {
                        // First number of a test case is always the color count
                        colorCount = it.toInt()
                        state = ReaderState.READ_CUSTOMER_NUMBER
                        customerNumber = 0
                        testNumber++
                        break@loop
                    }

                    ReaderState.READ_CUSTOMER_NUMBER -> {
                        // Next number after color count is always the number of customer cases
                        customerCount = it.toInt()
                        state = ReaderState.READ_CUSTOMER_CASE
                        lineResult = Array(colorCount) { -1 }
                        break@loop
                    }

                    ReaderState.READ_CUSTOMER_CASE -> {
                        // Read cutomer order line by line for all the customers
                        customerNumber++
                        if (customerNumber <= customerCount) {
                            val caseList = it.split(" ")
                            lineResult = processLine(caseList, lineResult)
                            var stringResult = ""
                            // When processing the last customer we can give the result and reset the state
                            if (customerNumber == customerCount) {
                                if (lineResult.isEmpty()) {
                                    stringResult = "Case #$testNumber: IMPOSSIBLE"
                                } else {
                                    stringResult = "Case #$testNumber: " + lineResult.joinToString(" ")
                                }

                                result[testNumber - 1] = stringResult
                                state = ReaderState.START_CASE
                            }
                        }
                        break@loop
                    }
                }
            }
        }

        return result
    }

    private fun processLine(caseList: List<String>, lineResult: Array<Int>): Array<Int> {
        val orderCount = caseList[0].toInt()
        var satisfiedColorCount = 0

        // First number of a case if the number of orders
        for (i in 0 until orderCount) {
            val colorNumber = caseList[i * 2 + 1].toInt()
            val colorType = caseList[i * 2 + 2].toInt()

            when (lineResult[colorNumber - 1]) {
                -1 -> {
                    lineResult[colorNumber - 1] = colorType
                    satisfiedColorCount++
                }

                colorType -> {
                    satisfiedColorCount++
                }
            }
        }

        if (satisfiedColorCount == 0) {
            return arrayOf()
        }

        return lineResult
    }
}

