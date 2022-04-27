package com.example.simplecalculator.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expressionparserutil.ExpressionParserUtil

class CalculatorActivityViewModel : ViewModel() {

    private val _displayTextLiveData = MutableLiveData<String>()
    val displayTextLiveData: LiveData<String>
        get() = _displayTextLiveData

    private val calculator = ExpressionParserUtil()

    fun numberInput(buttonValue: Int) {
        val text = _displayTextLiveData.value
        val newText = text + buttonValue.toString()
        _displayTextLiveData.value = newText
    }

    fun symbolInput(symbol: String) {
        val text = _displayTextLiveData.value!!

        fun updateDisplay() {
            val newText = "${text}$symbol"
            _displayTextLiveData.value = newText
        }

        when (symbol) {
            "÷", "x" -> if (text.isNotEmpty()) {
                if (text.last().isDigit() || text.last() == ')') updateDisplay()
            }

            "+", "-" -> updateDisplay()

            "(" -> if (text.isNotEmpty()) {
                if (text.last() == 'x' ||
                    text.last() == '÷' ||
                    text.last() == '-' ||
                    text.last() == '+' ||
                    text.last() == '('
                ) updateDisplay() else if (text.isNotEmpty() || text.last() == ')') {
                    val newText = "${text}x$symbol"
                    _displayTextLiveData.value = newText
                }
            }

            ")" -> if (text.isNotEmpty()) {
                if (text.last().isDigit() ||
                    text.last() == ')' ||
                    text.last() == '('
                ) updateDisplay()
            }

            "." -> if (text.isNotEmpty()) {
                if (text.last().isDigit()) updateDisplay()
            }
        }
    }

    fun equals() {

        if (calculator.parenthesesCheck(_displayTextLiveData.value.toString())) {
            val expression = _displayTextLiveData.value!!
            val result = calculator.calc(expression)
            _displayTextLiveData.value = result
        }
    }

    fun clear() {
        _displayTextLiveData.value = ""
    }

    init {
        _displayTextLiveData.value = ""
    }

    val divisionSymbol = "÷"
    val multiplicationSymbol = "x"
    val plusSymbol = "+"
    val minusSymbol = "-"
    val opParenthesesSymbol = "("
    val clParenthesesSymbol = ")"
    val commaSymbol = "."
}