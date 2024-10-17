package com.tops.kotlin.interviewleveltaskapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tops.kotlin.interviewleveltaskapp.databinding.ActivityGridRandomBinding

class GridRandomActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGridRandomBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGridRandomBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            submitNumber()
        }

        binding.btnGoToNext.setOnClickListener {
            startActivity(Intent(this, DropDownActivity::class.java))
        }
    }

    private fun submitNumber() {
        val number = binding.edtNumber.text.toString()
        val intent = Intent(this, GridViewRandomActivity::class.java)
        intent.putExtra("NUMBER", number)
        startActivity(intent)
    }
}