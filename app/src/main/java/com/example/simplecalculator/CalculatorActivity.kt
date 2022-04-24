package com.example.simplecalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.simplecalculator.databinding.CalculatorBinding
import com.example.simplecalculator.viewModel.CalculatorActivityViewModel

class CalculatorActivity : AppCompatActivity() {
    lateinit var binding: CalculatorBinding
    lateinit var viewModel: CalculatorActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CalculatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(CalculatorActivityViewModel::class.java)
        initListener()

        // Hide the status bar.
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        supportActionBar?.hide()

    }

    private fun initListener() {
        viewModel.mainDataLiveData.observe(this, Observer {
            binding.total.text = it.finalNum
        })
    }

    fun onNumClick(view: View) {
        viewModel.onNumClick((view as Button).text.toString())
    }

    fun onCalculateClick(view: View) {
        viewModel.onCalculateClick((view as Button).text.toString())
    }

    fun onEqualClick(view: View) {
        viewModel.onEqualClick()
    }

    fun onPercentClick(view: View) {
        viewModel.onPercentClick()
    }

    fun onClean(view: View) {
        viewModel.onCleanClick()
    }
}