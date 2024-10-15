package com.tops.kotlin.interviewleveltaskapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.tops.kotlin.interviewleveltaskapp.adapters.MatrixGridAdapter
import com.tops.kotlin.interviewleveltaskapp.adapters.MatrixRandomGridAdapter
import com.tops.kotlin.interviewleveltaskapp.databinding.ActivityGridViewRandomBinding

class GridViewRandomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGridViewRandomBinding
    private lateinit var matrixRandomGridAdapter: MatrixRandomGridAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGridViewRandomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val number = intent.getStringExtra("NUMBER")

        val matrix = Array(number!!.toInt()) { row ->
            Array(number.toInt()) { column -> "${row * number.toInt() + column + 1}" }
        }

        matrixRandomGridAdapter = MatrixRandomGridAdapter(matrix)

        // Setup RecyclerView
        binding.recyclerView.layoutManager = GridLayoutManager(this, number.toInt())
        binding.recyclerView.adapter = matrixRandomGridAdapter

        // Initial random selection
        matrixRandomGridAdapter.selectRandomRedItem()
    }
}