package com.tops.kotlin.interviewleveltaskapp

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.tops.kotlin.interviewleveltaskapp.databinding.ActivityDropDownBinding

class DropDownActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDropDownBinding
    private val categoryMap = mutableMapOf<String, MutableList<String>>()
    private lateinit var mainAdapter: ArrayAdapter<String>
    private val mainCategories = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDropDownBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize main category adapter and Spinner
        mainAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, mainCategories)
        mainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.dynamicSpinner.adapter = mainAdapter

        // Button click listener for adding main categories or sub-items
        binding.btnAdd.setOnClickListener {
            val newItem = binding.edtItem.text.toString().trim()
            if (newItem.isNotEmpty()) {
                if (binding.dynamicSpinner.selectedItem == null || newItem !in categoryMap) {
                    // Add as new main category
                    addMainCategory(newItem)
                } else {
                    // Add as sub-item to the selected main category
                    val selectedCategory = binding.dynamicSpinner.selectedItem.toString()
                    addSubItemToCategory(selectedCategory, newItem)
                }
                binding.edtItem.text.clear()
            } else {
                Toast.makeText(this, "Please enter a valid item", Toast.LENGTH_SHORT).show()
            }
        }

        // Set listener on main Spinner to display sub-items of selected category
        binding.dynamicSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val selectedCategory = mainCategories[position]
                    displaySubItemsForCategory(selectedCategory)
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // Do nothing
                }
            }
    }

    private fun addMainCategory(category: String) {
        if (category !in categoryMap) {
            // Add new main category with an empty sub-items list
            categoryMap[category] = mutableListOf()
            mainCategories.add(category)
            mainAdapter.notifyDataSetChanged()
            Toast.makeText(this, "$category added as a main category", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "$category already exists", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addSubItemToCategory(category: String, subItem: String) {
        val subItems = categoryMap[category]
        if (subItems != null && subItem !in subItems) {
            subItems.add(subItem)
            Toast.makeText(this, "$subItem added to $category", Toast.LENGTH_SHORT).show()
            displaySubItemsForCategory(category)
        } else {
            Toast.makeText(this, "$subItem already exists in $category", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displaySubItemsForCategory(category: String) {
        binding.spinnerContainer.removeAllViews()

        val subItems = categoryMap[category] ?: return
        if (subItems.isNotEmpty()) {
            val subCategorySpinner = Spinner(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }

            val subAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, subItems)
            subAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            subCategorySpinner.adapter = subAdapter

            binding.spinnerContainer.addView(subCategorySpinner)
        }
    }
}
