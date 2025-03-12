package com.example.btvnandroid

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var tvDisplay: TextView
    private var currentInput = ""
    private var firstOperand = 0.0
    private var operator: String? = null
    private var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvDisplay = findViewById(R.id.tvDisplay)

        val buttons = listOf(
            R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6,
            R.id.btn7, R.id.btn8, R.id.btn9, R.id.btnDot
        )
        buttons.forEach { id ->
            findViewById<Button>(id).setOnClickListener { appendNumber((it as Button).text.toString()) }
        }

        findViewById<Button>(R.id.btnPlus).setOnClickListener { setOperator("+") }
        findViewById<Button>(R.id.btnMinus).setOnClickListener { setOperator("-") }
        findViewById<Button>(R.id.btnMultiply).setOnClickListener { setOperator("*") }
        findViewById<Button>(R.id.btnDivide).setOnClickListener { setOperator("/") }
        findViewById<Button>(R.id.btnEqual).setOnClickListener { calculateResult() }
        findViewById<Button>(R.id.btnC).setOnClickListener { clear() }
        findViewById<Button>(R.id.btnCE).setOnClickListener { clearEntry() }
        findViewById<Button>(R.id.btnBS).setOnClickListener { backspace() }
        findViewById<Button>(R.id.btnSign).setOnClickListener { toggleSign() }
    }

    private fun appendNumber(number: String) {
        if (isNewOperation) {
            tvDisplay.text = ""
            isNewOperation = false
        }
        currentInput += number
        tvDisplay.text = currentInput
    }

    private fun setOperator(op: String) {
        if (currentInput.isNotEmpty()) {
            firstOperand = currentInput.toDouble()
            operator = op
            currentInput = ""
            isNewOperation = true
        }
    }

    private fun calculateResult() {
        if (currentInput.isNotEmpty() && operator != null) {
            val secondOperand = currentInput.toDouble()
            val result = when (operator) {
                "+" -> firstOperand + secondOperand
                "-" -> firstOperand - secondOperand
                "*" -> firstOperand * secondOperand
                "/" -> if (secondOperand != 0.0) firstOperand / secondOperand else "Error"
                else -> "Error"
            }
            tvDisplay.text = result.toString()
            currentInput = result.toString()
            isNewOperation = true
        }
    }

    private fun clear() {
        currentInput = ""
        firstOperand = 0.0
        operator = null
        tvDisplay.text = "0"
        isNewOperation = true
    }

    private fun clearEntry() {
        currentInput = ""
        tvDisplay.text = "0"
    }

    private fun backspace() {
        currentInput = if (currentInput.isNotEmpty()) currentInput.dropLast(1) else ""
        tvDisplay.text = if (currentInput.isNotEmpty()) currentInput else "0"
    }

    private fun toggleSign() {
        if (currentInput.isNotEmpty() && currentInput != "0") {
            currentInput = if (currentInput.startsWith("-")) {
                currentInput.substring(1)
            } else {
                "-$currentInput"
            }
            tvDisplay.text = currentInput
        }
    }
}