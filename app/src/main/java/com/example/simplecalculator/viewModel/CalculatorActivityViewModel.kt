package com.example.simplecalculator.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expressionparserutil.ExpressionParserUtil
import com.example.simplecalculator.model.CalculatorData

class CalculatorActivityViewModel : ViewModel() {

    private val _displayTextLiveData = MutableLiveData<String>()
    private val model = CalculatorData()
    val displayTextLiveData: LiveData<String>
        get() = _displayTextLiveData

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

    private val calculator = ExpressionParserUtil()

    fun numberInput(buttonValue: Int) {
        model.text = _displayTextLiveData.value
        model.newText = model.text + buttonValue.toString()
        _displayTextLiveData.value = model.newText!!
    }

    fun symbolInput(symbol: String) {
        model.text = _displayTextLiveData.value!!

        fun updateDisplay() {
            model.newText = "${model.text}$symbol"
            _displayTextLiveData.value = model.newText!!
        }

        when (symbol) {
            "÷", "x" -> if (model.text!!.isNotEmpty()) {
                if (model.text!!.last().isDigit() || model.text!!.last() == ')') updateDisplay()
            }

            "+", "-" -> updateDisplay()

            "(" -> if (model.text!!.isNotEmpty()) {
                if (model.text!!.last() == 'x' ||
                    model.text!!.last() == '÷' ||
                    model.text!!.last() == '-' ||
                    model.text!!.last() == '+' ||
                    model.text!!.last() == '('
                ) updateDisplay() else if (model.text!!.isNotEmpty() || model.text!!.last() == ')') {
                    val newText = "${model.text}x$symbol"
                    _displayTextLiveData.value = newText
                }
            }

            ")" -> if (model.text!!.isNotEmpty()) {
                if (model.text!!.last().isDigit() ||
                    model.text!!.last() == ')' ||
                    model.text!!.last() == '('
                ) updateDisplay()
            }

            "." -> if (model.text!!.isNotEmpty()) {
                if (model.text!!.last().isDigit()) updateDisplay()
            }
        }
    }

    fun equals() {
        if (calculator.parenthesesCheck(_displayTextLiveData.value.toString())) {
            model.expression = _displayTextLiveData.value!!
            model.result = calculator.calc(model.expression!!)
            _displayTextLiveData.value = model.result!!
        }
    }

    fun clear() {
        _displayTextLiveData.value = ""
    }

}