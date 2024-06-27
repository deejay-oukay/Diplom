package com.example.tmiz.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.tmiz.R
import com.example.tmiz.api.RetroBuilder
import com.example.tmiz.databinding.ActivityAnswerBinding
import com.example.tmiz.presentation.AnswersModel
import com.example.tmiz.presentation.CustomAdapter
import com.example.tmiz.presentation.NextActivity
import com.example.tmiz.presentation.State
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnswerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnswerBinding
    private val _state = MutableStateFlow<State>(State.Loading)
    private val state = _state.asStateFlow()
    private val _error = Channel<String>()
    private var code = 0
    private var question: String? = null
    private var multi: Boolean = false

    private lateinit var modelArrayList: ArrayList<AnswersModel>
    private lateinit var customAdapter: CustomAdapter
    private var answers: ArrayList<String> = arrayListOf(
        "Свой вариант (введите в поле ниже)",
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnswerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        modelArrayList = getModel(false)
        customAdapter = CustomAdapter(this, modelArrayList)
        binding.answersList.adapter = customAdapter

        binding.next.setOnClickListener {
            val intent = Intent(this@AnswerActivity, NextActivity::class.java)
            startActivity(intent)
        }

        binding.skipButton.setOnClickListener {
            randomQuestion()
        }

        lifecycleScope.launch {
            state.collect { state ->
                when (state) {
                    State.Default -> {
                        lifecycleScope.launch {
                            binding.statusLabel.text = getString(R.string.hint_answer_the_questions)
                            binding.answerButton.isEnabled = false
                        }
                    }
                    State.Loading -> {
                        lifecycleScope.launch {
                            binding.statusLabel.text = getString(R.string.state_loading)
                            binding.answersList.isVisible = false
                            binding.answerButton.isEnabled = false
                        }
                    }
                    State.Success -> {
                        lifecycleScope.launch {
                            binding.statusLabel.text = getString(R.string.hint_answer_the_questions)
                            binding.answerButton.isEnabled = true
                            binding.questionLabel.text = question.toString()
                            updateAnswers()
                            binding.answersList.isVisible = true
                        }
                    }
                    is State.Error -> {
                        lifecycleScope.launch {
                            binding.statusLabel.text = buildString {
                                append(getString(R.string.state_error))
                                append(" ")
                                append(state.error)
                                binding.answerButton.isEnabled = false
                            }
                        }
                    }
                }
            }
        }
        randomQuestion()
    }

    private fun randomQuestion() {
        lifecycleScope.launch {
            try {
                _state.value = State.Loading
                val result = RetroBuilder.api.questionsRandom()
                code = result.code()
                if (code == 200)
                {
                    question = result.body()?.body?.question.toString()
                    _state.value = State.Success
                    setAnswers(result.body()?.body?.answers)
                    updateAnswers()
                    multi = result.body()?.body?.multi!!
                }
                else
                    _state.value = State.Error(errors())
            } catch (e: Exception) {
                _state.value = State.Error(e.message.toString())
                _error.send(e.toString())
            }
        }
    }

    private fun getModel(isSelect: Boolean): ArrayList<AnswersModel> {
        val list = ArrayList<AnswersModel>()
        for (element in answers) {
            val model = AnswersModel()
            model.setSelecteds(isSelect)
            model.setAnswer(element)
            list.add(model)
        }
        return list
    }

    private fun setAnswers(al: Array<String>?) {
        answers.clear()
        if (!al.isNullOrEmpty())
            for (i in 0..<al.size) {
                answers.add(al[i])
            }
        answers.add("Свой вариант (введите в поле ниже)")
    }

    private fun updateAnswers() {
        modelArrayList = getModel(false)
        customAdapter = CustomAdapter(this, modelArrayList)
        binding.answersList.adapter = customAdapter
   }

    private fun errors(): String {
        if (code == 401)
            return getString(R.string.error_401)
        else if ((code == 204) || (question == null))
            return getString(R.string.error_204)
        else if (code >= 500)
            return getString(R.string.error_500_and_higher)
        return getString(R.string.error_unknown)
    }
}