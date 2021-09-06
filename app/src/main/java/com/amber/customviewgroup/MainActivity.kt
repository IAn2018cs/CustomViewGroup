package com.amber.customviewgroup

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val contentView = CalculatorLayout(this)
        setContentView(contentView)

        contentView.btn0.setOnClickListener {
            contentView.etResult.setText(contentView.btn0.text.toString())
        }
        contentView.btnDel.setOnClickListener {
            contentView.etResult.setText("")
        }
    }
}