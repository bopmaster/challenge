package com.example.challengelibrary

import java.io.InputStream

enum class ReaderState {
    NOT_STARTED,
    START_CASE,
    READ_CUSTOMER_NUMBER,
    READ_CUSTOMER_CASE
}

class PaintMaker {

    @Throws(NumberFormatException::class)
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
        var caseResult: Array<Int> = arrayOf()

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
                        /// First number of a test case is always the color count
                        colorCount = it.toInt()
                        state = ReaderState.READ_CUSTOMER_NUMBER
                        customerNumber = 0
                        testNumber++
                        break@loop
                    }

                    ReaderState.READ_CUSTOMER_NUMBER -> {
                        /// Next number after color count is always the number of customer cases
                        customerCount = it.toInt()
                        state = ReaderState.READ_CUSTOMER_CASE

                        /// Initialize result for each case, start with all -1 for all color
                        caseResult = Array(colorCount) { -1 }
                        break@loop
                    }

                    ReaderState.READ_CUSTOMER_CASE -> {
                        /// Read cutomer order line by line for all the customers
                        customerNumber++
                        if (customerNumber <= customerCount) {
                            val caseList = it.split(" ")
                            caseResult = processCustomerCase(caseList, caseResult)

                            /// When processing the last customer we can give the result and reset the state
                            if (customerNumber == customerCount) {
                                val stringResult = extractStringResult(caseResult, testNumber)

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

    private fun extractStringResult(caseResult: Array<Int>, testNumber: Int): String {
        val stringResult: String

        if (caseResult.isEmpty()) {
            stringResult = "Case #$testNumber: IMPOSSIBLE"
        } else {
            /// All the color which hasn't been order (value equal -1) will be made to glossy
            stringResult = "Case #$testNumber: " + caseResult.joinToString(" ").replace("-1", "0")
        }
        return stringResult
    }

    /** Process customer cases and result array which return:
     * -1 = color that no one order
     * 1 = matte color that someone order
     * 0 = glossy color that someone order
     * Empty array if there's not way to satisfy the orders
     */
    private fun processCustomerCase(caseList: List<String>, lineResult: Array<Int>): Array<Int> {
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

