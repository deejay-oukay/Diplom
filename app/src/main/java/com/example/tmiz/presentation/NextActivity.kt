package com.example.tmiz.presentation

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tmiz.R

class NextActivity : AppCompatActivity() {
    private var tv: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)
        tv = findViewById(R.id.tv) as TextView
        for (i in 0 until CustomAdapter.answersList!!.size) {
            if (CustomAdapter.answersList!!.get(i).getSelecteds())
                tv!!.text = tv!!.text.toString() + " " + CustomAdapter.answersList!!.get(i).getAnswer()
        }
    }
}