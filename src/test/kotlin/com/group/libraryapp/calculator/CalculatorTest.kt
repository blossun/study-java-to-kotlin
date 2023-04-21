package com.group.libraryapp.calculator

import org.junit.jupiter.api.Assertions.*
import java.lang.IllegalArgumentException

fun main() {
    val calculatorTest = CalculatorTest()
    calculatorTest.addTest()
    calculatorTest.minusTest()
    calculatorTest.multiplyTest()
    calculatorTest.divideTest()
    calculatorTest.divideExceptionTest()
}

class CalculatorTest {
    // test 결과 문제가 없다면 정상적으로 실행 후 종료되고, 문제가 있다면 Exception이 발생한다.
    fun addTest() {
        // given - test를 위한 준비
        val calculator = Calculator(5)

        // when - test하려는 기능을 호출
        calculator.add(3)

        // then - 호출 결과를 검증
        if (calculator.number != 8) {
            throw IllegalStateException()
        }
    }

    fun minusTest() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.minus(3)

        // then
        if (calculator.number != 2) {
            throw IllegalStateException()
        }
    }

    fun multiplyTest() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.multiply(3)

        // then
        if (calculator.number != 15) {
            throw IllegalStateException()
        }
    }

    // 0이 아닌 수로 나눈 경우
    fun divideTest() {
        // given
        val calculator = Calculator(5)

        // when
        calculator.divide(2)

        // then
        if (calculator.number != 2) {
            throw IllegalStateException()
        }
    }

    // 0으로 나눈 경우 Exception이 발생한다.
    fun divideExceptionTest() {
        // given
        val calculator = Calculator(5)

        // when
        try {
            calculator.divide(0)
        } catch (e: IllegalArgumentException) {
            // 테스트 성공!
            if (e.message != "0으로 나눌 수 없습니다.") {
                throw IllegalStateException("메시지가 다릅니다.")
            }
            return

        } catch (e: Exception) {
            throw IllegalStateException()
        }
        throw IllegalStateException("기대하는 Exception이 발생하지 않음")
    }

}
