package com.group.libraryapp.calculator

import org.junit.jupiter.api.Assertions.*

fun main() {
    val calculatorTest = CalculatorTest()
    calculatorTest.addTest()
}
class CalculatorTest {
    // test 결과 문제가 없다면 정상적으로 실행 후 종료되고, 문제가 있다면 Exception이 발생한다.
    fun addTest() {
        val calculator = Calculator(5)
        calculator.add(3)

        if (calculator.number != 8) {
            throw IllegalStateException()
        }
    }
}
