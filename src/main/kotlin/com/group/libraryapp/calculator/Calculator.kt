package com.group.libraryapp.calculator

import java.lang.IllegalArgumentException

class Calculator(
    private var _number: Int
) {

    val number: Int
        get() = this._number //내부 _number 값을 조회할 수 있는 public getter만 열어준다.

    fun add(operand: Int) {
        this._number += operand
    }

    fun minus(operand: Int) {
        this._number -= operand
    }

    fun multiply(operand: Int) {
        this._number *= operand
    }

    fun divide(operand: Int) {
        if (operand == 0) {
            throw IllegalArgumentException("0으로 나눌 수 없습니다.")
        }
        this._number /= operand
    }
}
