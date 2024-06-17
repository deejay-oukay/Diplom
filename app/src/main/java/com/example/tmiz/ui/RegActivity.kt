package com.example.tmiz.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tmiz.databinding.ActivityRegBinding

class RegActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}