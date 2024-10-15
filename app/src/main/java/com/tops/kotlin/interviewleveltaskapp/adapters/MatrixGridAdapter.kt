package com.tops.kotlin.interviewleveltaskapp.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.tops.kotlin.interviewleveltaskapp.databinding.RecyclerGridItemBinding

class MatrixGridAdapter(private val matrix: Array<Array<String>>) :
    Adapter<MatrixGridAdapter.ViewHolder>() {

    private val selectedItems = mutableSetOf<Pair<Int, Int>>()

    @SuppressLint("NotifyDataSetChanged")
    inner class ViewHolder(val binding: RecyclerGridItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                val position = adapterPosition
                val row = position / matrix.size
                val col = position % matrix.size

                // Clear previous selections
                selectedItems.clear()

                // Select all items in the current row
                for (i in matrix.indices) {
                    selectedItems.add(Pair(row, i)) // Select all items in the row
                }

                // Select all items in the current column
                for (i in matrix.indices) {
                    selectedItems.add(Pair(i, col)) // Select all items in the column
                }

                // Select diagonal items corresponding to the clicked item
                for (i in matrix.indices) {
                    // Select items for upper diagonal
                    if (row - i >= 0 && col - i >= 0) {
                        selectedItems.add(Pair(row - i, col - i)) // Add upper left diagonal item
                    }
                    if (row - i >= 0 && col + i < matrix.size) {
                        selectedItems.add(Pair(row - i, col + i)) // Add upper right diagonal item
                    }
                    // Select items for lower diagonal
                    if (row + i < matrix.size && col - i >= 0) {
                        selectedItems.add(Pair(row + i, col - i)) // Add lower left diagonal item
                    }
                    if (row + i < matrix.size && col + i < matrix.size) {
                        selectedItems.add(Pair(row + i, col + i)) // Add lower right diagonal item
                    }
                }

                // Notify that the data set has changed to update the UI
                notifyDataSetChanged()
            }
        }

        // Update item view based on whether it is selected or not
        fun bind(item: String, isSelected: Boolean) {
            binding.tvItem.text = item
            if (isSelected) {
                binding.cardView.setCardBackgroundColor(Color.YELLOW)
                binding.tvItem.setTextColor(Color.BLACK)
            } else {
                binding.cardView.setCardBackgroundColor(Color.BLUE)
                binding.tvItem.setTextColor(Color.WHITE)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            RecyclerGridItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return matrix.size * matrix.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val row = position / matrix.size
        val col = position % matrix.size
        val isSelected = selectedItems.contains(Pair(row, col))
        holder.bind(matrix[row][col], isSelected)
        holder.binding.tvItem.text = matrix[row][col]
    }
}