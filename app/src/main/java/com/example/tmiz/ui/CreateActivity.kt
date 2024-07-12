package com.example.tmiz.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.tmiz.R
import com.example.tmiz.api.RetroBuilder
import com.example.tmiz.databinding.ActivityCreateBinding
import com.example.tmiz.states.StateCreate
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBinding
    private val _stateCreate = MutableStateFlow<StateCreate>(StateCreate.Default)
    private val state = _stateCreate.asStateFlow()
    private val _error = Channel<String>()
    private var code = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.multiLabel.setOnClickListener {
            binding.multiCheckbox.isChecked = !binding.multiCheckbox.isChecked
        }
        binding.createButton.setOnClickListener {
            lifecycleScope.launch {
                try {
                    _stateCreate.value = StateCreate.Loading
                    val result = RetroBuilder.api.questionCreate(
                        binding.questionInput.text.toString(),
                        binding.answersInput.text.toString(),
                        binding.multiCheckbox.isChecked
                    )
                    code = result.code()
                    if (code == 201)
                        _stateCreate.value = StateCreate.Success
                    else
                        _stateCreate.value = StateCreate.Error(errors())
                } catch (e: Exception) {
                    _stateCreate.value = StateCreate.Error(e.message.toString())
                    _error.send(e.toString())
                }
            }
        }
        lifecycleScope.launch {
            state.collect { state ->
                when (state) {
                    StateCreate.Default -> {
                        lifecycleScope.launch {
                            binding.statusLabel.text = ""
                        }
                    }
                    StateCreate.Loading -> {
                        lifecycleScope.launch {
                            binding.statusLabel.text = getString(R.string.state_loading)
                        }
                        binding.createButton.isEnabled = false
                    }
                    StateCreate.Success -> {
                        lifecycleScope.launch {
                            binding.statusLabel.text = getString(R.string.state_success)
                        }
                        binding.createButton.isEnabled = true
                        binding.questionInput.setText("")
                        binding.answersInput.setText("")
                        binding.multiCheckbox.isChecked = false
                    }
                    is StateCreate.Error -> {
                        lifecycleScope.launch {
                            binding.statusLabel.text = buildString {
                                append(getString(R.string.state_error))
                                append(" ")
                                append(state.error)
                            }
                        }
                        binding.createButton.isEnabled = true
                    }
                }
            }
        }
    }
    private fun errors(): String {
        if (code == 400)
            return getString(R.string.error_400)
        else if (code == 401)
            return getString(R.string.error_401)
        else if (code == 409)
            return getString(R.string.error_409_question)
        else if (code >= 500)
            return getString(R.string.error_500_and_higher)
        return getString(R.string.error_unknown)
    }
}