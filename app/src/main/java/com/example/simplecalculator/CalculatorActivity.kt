package com.example.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import android.text.method.ScrollingMovementMethod
import android.view.View
import com.example.simplecalculator.databinding.CalculatorBinding
import com.example.simplecalculator.viewModel.CalculatorActivityViewModel

class CalculatorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: CalculatorBinding = CalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.display.movementMethod = ScrollingMovementMethod()

        val viewModel = ViewModelProvider(this).get(CalculatorActivityViewModel::class.java)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()
    }
}