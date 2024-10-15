package com.tops.kotlin.interviewleveltaskapp.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.tops.kotlin.interviewleveltaskapp.databinding.RecyclerGridItemBinding
import java.util.Random

class MatrixRandomGridAdapter(private val matrix: Array<Array<String>>) :
    Adapter<MatrixRandomGridAdapter.ViewHolder>() {

    private val greenItems = mutableSetOf<Pair<Int, Int>>() // Track green items
    private var redItem: Pair<Int, Int>? = null // Track the current red item
    private val random = Random()

    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(val binding: RecyclerGridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                val row = position / matrix.size
                val col = position % matrix.size

                // Check if the clicked item is the current red item
                if (Pair(row, col) == redItem) {
                    greenItems.add(Pair(row, col)) // Mark the item as green
                    redItem = null // Clear the current red item
                    notifyDataSetChanged() // Update UI

                    // Select a new random item
                    selectRandomRedItem()
                }
            }
        }

        fun bind(item: String, isGreen: Boolean, isRed: Boolean) {
            binding.tvItem.text = item
            when {
                isGreen -> {
                    binding.root.setCardBackgroundColor(Color.GREEN) // Green color for selected items
                    binding.tvItem.setTextColor(Color.BLACK)
                }

                isRed -> {
                    binding.root.setCardBackgroundColor(Color.RED) // Red color for clickable item
                    binding.tvItem.setTextColor(Color.WHITE)
                }

                else -> {
                    binding.root.setCardBackgroundColor(Color.parseColor("#FF6200EE")) // Default color
                    binding.tvItem.setTextColor(Color.WHITE)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RecyclerGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val row = position / matrix.size
        val col = position % matrix.size
        val isGreen = greenItems.contains(Pair(row, col))
        val isRed = redItem == Pair(row, col)
        holder.bind(matrix[row][col], isGreen, isRed)
    }

    override fun getItemCount(): Int {
        return matrix.size * matrix.size // Total items in the grid
    }

    // Method to select a random item as the next red item
    @SuppressLint("NotifyDataSetChanged")
    fun selectRandomRedItem() {
        val availableItems = mutableListOf<Pair<Int, Int>>()
        for (row in matrix.indices) {
            for (col in matrix[row].indices) {
                val item = Pair(row, col)
                if (item !in greenItems) {
                    availableItems.add(item)
                }
            }
        }

        if (availableItems.isNotEmpty()) {
            redItem = availableItems[random.nextInt(availableItems.size)]
            notifyDataSetChanged() // Update the UI to show the new red item
        }
    }
}