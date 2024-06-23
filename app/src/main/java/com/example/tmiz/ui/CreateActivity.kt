package com.example.tmiz.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tmiz.R
import com.example.tmiz.api.ApiException
import com.example.tmiz.api.RetroBuilder
import com.example.tmiz.databinding.ActivityCreateBinding
import kotlinx.coroutines.launch

class CreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.multiLabel.setOnClickListener {
            binding.multiCheckbox.isChecked = !binding.multiCheckbox.isChecked
        }
        binding.createButton.setOnClickListener {
            lifecycleScope.launch {
                val response = RetroBuilder.api.questionCreate(
                    binding.questionInput.text.toString(),
                    binding.answersInput.text.toString(),
                    binding.multiCheckbox.isChecked
                )
                if(!response.isSuccessful) {
                    throw ApiException(R.string.request_failed.toString())
                } else
                    binding.questionInput.setText(response.body().toString())
            }
        }
   }
}