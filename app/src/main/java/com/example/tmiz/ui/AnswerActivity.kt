package com.example.tmiz.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.tmiz.R
import com.example.tmiz.api.RetroBuilder
import com.example.tmiz.databinding.ActivityAnswerBinding
import com.example.tmiz.presentation.AnswersModel
import com.example.tmiz.presentation.CustomAdapter
import com.example.tmiz.presentation.StateAnswer
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AnswerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnswerBinding
    private val _stateAnswer = MutableStateFlow<StateAnswer>(StateAnswer.Loading)
    private val state = _stateAnswer.asStateFlow()
    private val _error = Channel<String>()
    private var code = 0
    private var question: String? = null
    private var questionId: String = ""
    private var multi: Boolean = false
    private lateinit var answersIds: Array<String>
    private var selectedAnswers: ArrayList<String> = arrayListOf()

    private lateinit var modelArrayList: ArrayList<AnswersModel>
    private lateinit var customAdapter: CustomAdapter
    private var answers: ArrayList<String> = arrayListOf(
        R.string.new_answer_spacer.toString(),
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnswerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        modelArrayList = getModel(false)
        customAdapter = CustomAdapter(this, modelArrayList)
        binding.answersList.adapter = customAdapter

        binding.answerButton.setOnClickListener {
            for (elem in CustomAdapter.answersList) {
                if (elem.getSelecteds()) {
                    try {
                        selectedAnswers.add(elem.getAnswer())
                    } catch (e: Exception) {
                        _stateAnswer.value = StateAnswer.ErrorSend(e.message.toString())
                    }
                }
            }
            if (!binding.newAnswerInput.text.isNullOrEmpty())
                selectedAnswers.add(binding.newAnswerInput.text.toString())
            lifecycleScope.launch {
                try {
                    _stateAnswer.value = StateAnswer.Sending
                    val result = RetroBuilder.api.answersSend(questionId, selectedAnswers.toTypedArray())
                    code = result.code()
                    if (code == 202)
                    {
                        _stateAnswer.value = StateAnswer.SuccessSend
                        selectedAnswers.clear()
                        //answersIds = result.body()?.body?.answersIds!!
                    }
                    else
                        _stateAnswer.value = StateAnswer.ErrorSend(errors())
                } catch (e: Exception) {
                    _stateAnswer.value = StateAnswer.ErrorSend(e.message.toString())
                    _error.send(e.toString())
                }
            }
        }

        binding.skipButton.setOnClickListener {
            randomQuestion()
        }

        lifecycleScope.launch {
            state.collect { state ->
                when (state) {
                    StateAnswer.Loading -> {
                        lifecycleScope.launch {
                            binding.statusLabel.text = getString(R.string.state_loading)
                            binding.answersList.isVisible = false
                            binding.answerButton.isEnabled = false
                            binding.newAnswerLayout.isVisible = false
                        }
                    }
                    StateAnswer.SuccessGet -> {
                        lifecycleScope.launch {
                            binding.statusLabel.text = getString(R.string.hint_answer_the_questions)
                            binding.answerButton.isEnabled = true
                            binding.questionLabel.text = question.toString()
                            updateAnswers()
                            binding.answersList.isVisible = true
                            binding.newAnswerInput.text = null
                            binding.newAnswerLayout.isVisible = true
                        }
                    }
                    is StateAnswer.ErrorGet -> {
                        lifecycleScope.launch {
                            binding.statusLabel.text = buildString {
                                append(getString(R.string.state_error))
                                append(" ")
                                append(state.error)
                                binding.answerButton.isEnabled = false
                            }
                            binding.newAnswerLayout.isVisible = false
                        }
                    }
                    StateAnswer.Sending -> {
                        lifecycleScope.launch {
                            binding.statusLabel.text = getString(R.string.state_loading)
                            binding.answerButton.isEnabled = false
                        }
                    }
                    is StateAnswer.ErrorSend -> {
                        lifecycleScope.launch {
                            binding.statusLabel.text = buildString {
                                append(getString(R.string.state_error))
                                append(" ")
                                append(state.error)
                                binding.answerButton.isEnabled = true
                            }
                        }
                    }
                    StateAnswer.SuccessSend -> {
                        lifecycleScope.launch {
                            binding.statusLabel.text = getString(R.string.label_answer_accepted)
                            binding.answerButton.isEnabled = false
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
                _stateAnswer.value = StateAnswer.Loading
                val result = RetroBuilder.api.questionsRandom()
                code = result.code()
                if (code == 200)
                {
                    question = result.body()?.body?.question.toString()
                    questionId = result.body()?.body?.questionId.toString()
                    _stateAnswer.value = StateAnswer.SuccessGet
                    setAnswers(result.body()?.body?.answers)
                    updateAnswers()
                    multi = result.body()?.body?.multi!!
                }
                else
                    _stateAnswer.value = StateAnswer.ErrorGet(errors())
            } catch (e: Exception) {
                _stateAnswer.value = StateAnswer.ErrorGet(e.message.toString())
                _error.send(e.toString())
            }
        }
    }

    fun getModel(isSelect: Boolean): ArrayList<AnswersModel> {
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
        answers.add(getString(R.string.new_answer_spacer))
    }

    private fun updateAnswers() {
        modelArrayList = getModel(false)
        customAdapter = CustomAdapter(this, modelArrayList)
        binding.answersList.adapter = customAdapter
   }

    private fun errors(): String {
        if (code == 400)
            return getString(R.string.error_400)
        else if (code == 401)
            return getString(R.string.error_401)
        else if (code == 404)
            return getString(R.string.error_404)
        else if ((code == 204) || (question == null))
            return getString(R.string.error_204)
        else if (code >= 500)
            return getString(R.string.error_500_and_higher)
        return getString(R.string.error_unknown)
    }
}