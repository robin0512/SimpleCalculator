package com.example.simplecalculator.model

data class CalculatorData(
    var newState: Boolean = true,
    var hasPoint: Boolean = false,
    var oldNum: String = "",
    var finalNum: String = "",
    var calculateType: String = ""
)