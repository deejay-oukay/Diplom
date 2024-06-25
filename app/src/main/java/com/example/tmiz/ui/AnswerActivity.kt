package com.example.tmiz.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.tmiz.R
import com.example.tmiz.databinding.ActivityAnswerBinding

class AnswerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAnswerBinding
    private var answersArray = arrayListOf(
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
        //title = "Здесь будет текст вопроса длинный очень!!!"

        val adapter = ArrayAdapter(this, R.layout.answers,  answersArray)
        val listView: ListView = binding.answersList
        listView.setAdapter(adapter)
    }
}