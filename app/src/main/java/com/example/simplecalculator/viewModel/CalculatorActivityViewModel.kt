package com.example.simplecalculator.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.simplecalculator.model.CalculatorData

class CalculatorActivityViewModel : ViewModel()  {
    val mainDataLiveData: MutableLiveData<CalculatorData> = MutableLiveData()
    private var mainData = CalculatorData()

    fun onNumClick(keyString: String) {
        if (mainData.newState) {
            mainData.finalNum = ""
            mainDataLiveData.value = mainData
        }
        mainData.newState = false
        var totalString = mainData.finalNum

        when (keyString) {
            "." -> {
                if (!mainData.hasPoint) {
                    totalString += "."
                    mainData.hasPoint = true
                }
            }
            "+/-" -> {
                totalString = if (totalString.startsWith("-"))
                    totalString.replace("-", "")
                else
                    "-$totalString"
            }
            else -> totalString += keyString

        }
        mainData.finalNum = totalString
        mainDataLiveData.value = mainData
    }


    fun onCalculateClick(keyString: String) {
        mainData.calculateType = keyString
        mainData.oldNum = mainData.finalNum
        mainData.newState = true
        mainData.hasPoint = false
    }

    fun onEqualClick() {
        var finalDouble = 0.0
        when (mainData.calculateType) {
            "+" -> {
                finalDouble = mainData.oldNum.toDouble() + mainData.finalNum.toDouble()
            }
            "-" -> {
                finalDouble = mainData.oldNum.toDouble() - mainData.finalNum.toDouble()
            }
            "*" -> {
                finalDouble = mainData.oldNum.toDouble() * mainData.finalNum.toDouble()
            }
            "/" -> {
                finalDouble = mainData.oldNum.toDouble() / mainData.finalNum.toDouble()
            }
        }
        mainData.finalNum = finalDouble.toString()
        mainData.newState = true
        mainData.oldNum = mainData.finalNum
        mainDataLiveData.value = mainData
    }

    fun onPercentClick() {
        mainData.finalNum = (mainData.finalNum.toDouble() / 100).toString()
        mainData.newState = true
        mainData.oldNum = mainData.finalNum
        mainDataLiveData.value = mainData
    }

    fun onCleanClick() {
        mainData = CalculatorData()
        mainData.finalNum = "0"
        mainDataLiveData.value = mainData

    }
}