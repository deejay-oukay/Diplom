package com.example.tmiz.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.tmiz.R
import com.example.tmiz.adapters.QuestionAdapter
import com.example.tmiz.adapters.QuestionsModel
import com.example.tmiz.api.Question
import com.example.tmiz.api.RetroBuilder
import com.example.tmiz.databinding.ActivityMineBinding
import com.example.tmiz.states.StateMine
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MineActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMineBinding
    private val _stateMine = MutableStateFlow<StateMine>(StateMine.Loading)
    private val state = _stateMine.asStateFlow()
    private val _error = Channel<String>()
    private var code = 0
    private var questions: ArrayList<Question> = arrayListOf(Question("",""))
    private lateinit var modelArrayList: ArrayList<QuestionsModel>
    private lateinit var adapter: QuestionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMineBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.refreshButton.setOnClickListener { myQuestions() }
        lifecycleScope.launch {
            state.collect { state ->
                when (state) {
                    StateMine.Loading -> {
                        lifecycleScope.launch {
                            binding.statusLabel.text = getString(R.string.state_loading)
                            binding.questionsList.isVisible = false
                            binding.refreshButton.isVisible = false
                        }
                    }
                    StateMine.Success -> {
                        lifecycleScope.launch {
                            binding.statusLabel.text = getString(R.string.hint_select_the_questions)
                            binding.refreshButton.isVisible = false
                            updateQuestions()
                        }
                    }
                    is StateMine.Error -> {
                        lifecycleScope.launch {
                            binding.statusLabel.text = buildString {
                                append(getString(R.string.state_error))
                                append(" ")
                                append(state.error)
                                binding.refreshButton.isVisible = true
                            }
                            binding.questionsList.isVisible = false
                        }
                    }
                }
            }
        }
        myQuestions()
    }

    private fun myQuestions() {
        lifecycleScope.launch {
            try {
                _stateMine.value = StateMine.Loading
                val result = RetroBuilder.api.questionsMine()
                code = result.code()
                if (code == 200)
                {
                    _stateMine.value = StateMine.Success
                    setQuestions(result.body()?.body?.questionsIds,result.body()?.body?.questions)
                    updateQuestions()
                    binding.questionsList.isVisible = true
                }
                else
                    _stateMine.value = StateMine.Error(errors())
            } catch (e: Exception) {
                _stateMine.value = StateMine.Error(e.message.toString())
                _error.send(e.toString())
            }
        }
    }
    private fun getModel(): ArrayList<QuestionsModel> {
        val list = ArrayList<QuestionsModel>()
        for (element in questions) {
            val model = QuestionsModel()
            model.setId(element.questionId)
            model.setText(element.question)
            list.add(model)
        }
        return list
    }
    private fun updateQuestions() {
        modelArrayList = getModel()
        adapter = QuestionAdapter(this, modelArrayList)
        binding.questionsList.adapter = adapter

    }
    private fun setQuestions(ids: Array<String>?, texts: Array<String>?) {
        questions.clear()
        if (!ids.isNullOrEmpty() && (!texts.isNullOrEmpty()))
            for (i in ids.indices)
                questions.add(Question(ids[i], texts[i]))
    }
    private fun errors(): String {
        if (code == 401)
            return getString(R.string.error_401)
        else if ((code == 204) || (questions.isEmpty()))
            return getString(R.string.error_204)
        else if (code >= 500)
            return getString(R.string.error_500_and_higher)
        return getString(R.string.error_unknown)
    }
}