package com.tops.kotlin.interviewleveltaskapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tops.kotlin.interviewleveltaskapp.databinding.ActivityGridBinding

class GridActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGridBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGridBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSubmit.setOnClickListener {
            submitNumber()
        }

        binding.btnGoToNext.setOnClickListener {
            startActivity(Intent(this, GridRandomActivity::class.java))
        }
    }

    private fun submitNumber() {
        val number = binding.edtNumber.text.toString()
        val intent = Intent(this, GridViewActivity::class.java)
        intent.putExtra("NUMBER", number)
        startActivity(intent)
    }
}