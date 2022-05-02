package com.example.simplecalculator

import android.R
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.simplecalculator.databinding.CalculatorBinding
import com.example.simplecalculator.viewModel.CalculatorActivityViewModel


class CalculatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: CalculatorBinding = CalculatorBinding.inflate(layoutInflater)
        val view= binding.root
        setContentView(view)

        val viewModel = ViewModelProvider(this).get(CalculatorActivityViewModel::class.java)
            .apply {
            equalClickEvent.observe(this@CalculatorActivity,
                Observer { equalClick ->
                    if (equalClick == true) {
                        println("equalClickTrue")

                    }else
                        println("equalClickFalse")

                })
        }

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
    }
}