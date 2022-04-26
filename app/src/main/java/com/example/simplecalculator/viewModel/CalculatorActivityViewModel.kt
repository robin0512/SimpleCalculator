package com.example.simplecalculator.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.expressionparserutil.ExpressionParserUtil

class CalculatorActivityViewModel : ViewModel()  {
    //LiveData object used to update the UI expressions display
    private val _displayTextLiveData = MutableLiveData<String>()
    val displayTextLiveData: LiveData<String>
        get() = _displayTextLiveData

    //LiveData object used to update the UI error messages display
    private val _errorTextLiveData = MutableLiveData<String>()
    val errorTextLiveData: LiveData<String>
        get() = _errorTextLiveData

    //Expression parser and calculator imported via jitpack.
    private val calculator = ExpressionParserUtil()

    //Error messages to be displayed in case of invalid input
    private val genericError = "invalid expression"
    private val parenthesesError = "mismatched parentheses"
    private val emptyExpressionError = "enter an expression"

    //Click function for numeric input
    fun numberInput(buttonValue: Int) {
        if (_errorTextLiveData.value.toString().isNotEmpty()) clearErrorMessage()

        val text = _displayTextLiveData.value
        val newText = text + buttonValue.toString()
        _displayTextLiveData.value = newText
    }

    //Click function for symbols input - it stops the user from inputting some invalid expressions.
    fun symbolInput(symbol: String) {
        if (_errorTextLiveData.value.toString().isNotEmpty()) clearErrorMessage()

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
                if (text.last() == 'x'||
                    text.last() == '÷'||
                    text.last() == '-'||
                    text.last() == '+'||
                    text.last() == '(') updateDisplay() else if (text.isNotEmpty() || text.last() == ')') {
                    val newText = "${text}x$symbol"
                    _displayTextLiveData.value = newText
                }
            }

            ")" -> if (text.isNotEmpty()) {
                if (text.last().isDigit() ||
                    text.last() == ')' ||
                    text.last() == '(') updateDisplay()
            }

            "." -> if (text.isNotEmpty()) {
                if (text.last().isDigit()) updateDisplay()
            }
        }
    }

    //Delete function - deletes last character from display
    fun delete() {
        if (_errorTextLiveData.value.toString().isNotEmpty()) clearErrorMessage()

        val text = _displayTextLiveData.value!!
        val newText = text.dropLast(1)
        _displayTextLiveData.value = newText
    }

    //Equal button - calls the "calc" function on the calculator object and handles
    //invalid expressions, displaying the error on the _errorTextLiveData.
    fun equals() {
        if (_displayTextLiveData.value!!.isEmpty()) {
            emptyExpressionError()
            return
        }
        if (calculator.parenthesesCheck(_displayTextLiveData.value.toString())) {
            try {
                val expression = _displayTextLiveData.value!!
                val result = calculator.calc(expression)
                _displayTextLiveData.value = result
            } catch (e: Exception) {
                genericError()
            }
        } else parenthesesError()
    }

    //Generic error in case of unexpected invalid expressions that might throw exceptions.
    private fun genericError() {
        _errorTextLiveData.value = genericError
    }

    //In case of invalid parentheses configuration
    private fun parenthesesError() {
        _errorTextLiveData.value = parenthesesError
    }

    private fun emptyExpressionError() {
        _errorTextLiveData.value = emptyExpressionError
    }

    //Clear button
    fun clear() {
        if (_errorTextLiveData.value.toString().isNotEmpty()) clearErrorMessage()

        _displayTextLiveData.value = ""
    }

    //Clears the error messages
    private fun clearErrorMessage() {
        _errorTextLiveData.value = ""
    }

    init {
        _displayTextLiveData.value = ""
    }

    //Variables with symbols for xml access
    val divisionSymbol = "÷"
    val multiplicationSymbol = "x"
    val plusSymbol = "+"
    val minusSymbol = "-"
    val opParenthesesSymbol = "("
    val clParenthesesSymbol = ")"
    val commaSymbol = "."
}