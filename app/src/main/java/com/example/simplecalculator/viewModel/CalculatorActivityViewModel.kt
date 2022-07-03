package com.example.simplecalculator.viewModel

import android.R
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expressionparserutil.ExpressionParserUtil
import com.example.simplecalculator.model.CalculatorData


class CalculatorActivityViewModel : ViewModel() {

    private val model = CalculatorData()
    private val _displayExpressionLiveData = MutableLiveData<String>()
    val displayExpressionLiveData: LiveData<String>
        get() = _displayExpressionLiveData

    private val _displayTotalLiveData = MutableLiveData<String>()
    val displayTotalLiveData: LiveData<String>
        get() = _displayTotalLiveData

    init {
        _displayExpressionLiveData.value = ""
    }

    val divisionSymbol = "÷"
    val multiplicationSymbol = "x"
    val plusSymbol = "+"
    val minusSymbol = "-"
    val openParenthesesSymbol = "("
    val closeParenthesesSymbol = ")"
    val commaSymbol = "."
    val equalClickEvent = MutableLiveData<Boolean>(false)
    val resetTextSize = MutableLiveData<Boolean>(false)

    private val calculator = ExpressionParserUtil()

    fun numberInput(buttonValue: Int) {
        val numText = _displayExpressionLiveData.value
        val newNumText = numText + buttonValue.toString()
        _displayExpressionLiveData.value = newNumText
        calculate()
    }

    fun symbolInput(symbol: String) {
        val symbolText = _displayExpressionLiveData.value!!
        fun updateDisplay() {
            val newSymbolText = "${symbolText}$symbol"
            _displayExpressionLiveData.value = newSymbolText
        }

        when (symbol) {
            "÷", "x" -> if (symbolText.isNotEmpty()) {
                if (symbolText.last().isDigit() || symbolText.last() == ')') updateDisplay()
            }

            "+", "-" -> updateDisplay()

            "(" -> if (symbolText.isNotEmpty()) {
                if (symbolText.last() == 'x' ||
                    symbolText.last() == '÷' ||
                    symbolText.last() == '-' ||
                    symbolText.last() == '+' ||
                    symbolText.last() == '('
                ) updateDisplay() else if (symbolText.isNotEmpty() || symbolText.last() == ')') {
                    val newText = "${symbolText}x$symbol"
                    _displayExpressionLiveData.value = newText
                }
            }

            ")" -> if (symbolText.isNotEmpty()) {
                if (symbolText.last().isDigit() ||
                    symbolText.last() == ')' ||
                    symbolText.last() == '('
                ) updateDisplay()
                calculate()
            }

            "." -> if (symbolText.isNotEmpty()) {
                if (symbolText.last().isDigit()) updateDisplay()
            }
        }
    }

    private fun calculate() {
        if (calculator.parenthesesCheck(_displayExpressionLiveData.value.toString())) {
            model.expression = _displayExpressionLiveData.value!!
            model.result = calculator.calc(model.expression!!)
            _displayTotalLiveData.value = model.result!!
        }
    }

    fun equalClick() {
        equalClickEvent.value = true
    }

    fun clear() {
        _displayExpressionLiveData.value = ""
        _displayTotalLiveData.value = ""
        resetTextSize.value = true
    }

}