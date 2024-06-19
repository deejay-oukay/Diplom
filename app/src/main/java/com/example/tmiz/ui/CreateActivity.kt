package com.example.tmiz.ui

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.tmiz.R
import com.example.tmiz.databinding.ActivityCreateBinding

class CreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBinding
    //Если указать в кавычках текст, то он добавится в значение поля
    //Как добавить его в подсказку к полю - не понял
    //Можно сразу добавить несколько элементов - будет сразу несколько вариантов ответов
    private var answersArray = arrayListOf("")
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var listView: ListView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = ArrayAdapter(this, R.layout.answers_on_create,  answersArray)
        listView = findViewById(R.id.answers_list)
        listView.setAdapter(adapter)

        binding.addButton.setOnClickListener {
            //добавляем поле для ввода ещё одного варианта ответа
            answersArray.add("")
            //отображаем изменения
            listView.setAdapter(adapter)
            //делаем доступной кнопку удаления
            binding.delButton.isEnabled = true
        }

        binding.delButton.setOnClickListener {
            //удаляем поле для ввода последнего варианта ответа
            answersArray.removeAt(answersArray.size-1)
            //отображаем изменения
            listView.setAdapter(adapter)
            //если список пуст, то делаем недоступной кнопку удаления
            if (answersArray.size == 0)
                binding.delButton.isEnabled = false
            //если в список меньше двух вариантов, снимаем галочку
            if (answersArray.size < 2)
                binding.multiCheckbox.isChecked = false
        }

        binding.multiCheckbox.setOnClickListener {
            multiCheckUnCheck()
        }
        binding.multiLabel.setOnClickListener {
            binding.multiCheckbox.isChecked = !binding.multiCheckbox.isChecked
            multiCheckUnCheck()
        }

   }

    private fun multiCheckUnCheck(){
        //если гачлока отмечена
        if (binding.multiCheckbox.isChecked) {
            //добавляем пункты, пока их не станет хотя бы 2
            while (answersArray.size < 2)
                answersArray.add("")
            //отображаем изменения
            listView.setAdapter(adapter)
        }
    }
}