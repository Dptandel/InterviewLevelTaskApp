package com.tops.kotlin.interviewleveltaskapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.tops.kotlin.interviewleveltaskapp.adapters.MatrixGridAdapter
import com.tops.kotlin.interviewleveltaskapp.databinding.ActivityGridViewBinding

class GridViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGridViewBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGridViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val number = intent.getStringExtra("NUMBER")

        val matrix = Array(number!!.toInt()) { row ->
            Array(number.toInt()) { column -> "${row * number.toInt() + column + 1}" }
        }

        binding.recyclerView.layoutManager = GridLayoutManager(this, number.toInt())
        binding.recyclerView.adapter = MatrixGridAdapter(matrix)
    }
}