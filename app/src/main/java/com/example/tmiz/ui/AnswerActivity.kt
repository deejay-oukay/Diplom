package com.example.tmiz.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tmiz.databinding.ActivityAnswerBinding
import com.example.tmiz.presentation.AnswersModel
import com.example.tmiz.presentation.CustomAdapter
import com.example.tmiz.presentation.NextActivity

class AnswerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnswerBinding
    private lateinit var modelArrayList: ArrayList<AnswersModel>
    private lateinit var customAdapter: CustomAdapter
    private val variants = arrayOf(
        "Вариант ответа 1",
        "Вариант ответа 2",
        "Вариант ответа 3",
        "Вариант ответа 4",
        "Вариант ответа 5",
        "Вариант ответа 6",
        "Вариант ответа 1",
        "Вариант ответа 2",
        "Вариант ответа 3",
        "Вариант ответа 4",
        "Вариант ответа 5",
        "Вариант ответа 6",
        "Вариант ответа 1",
        "Вариант ответа 2",
        "Вариант ответа 3",
        "Вариант ответа 4",
        "Вариант ответа 5",
        "Вариант ответа 6",
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
    }

    private fun getModel(isSelect: Boolean): ArrayList<AnswersModel> {
        val list = ArrayList<AnswersModel>()
        for (element in variants) {
            val model = AnswersModel()
            model.setSelecteds(isSelect)
            model.setAnswer(element)
            list.add(model)
        }
        return list
    }
}