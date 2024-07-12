package com.example.tmiz.adapters

class AnswersModel {
    var isSelected: Boolean = false
    var text: String? = null

    fun getAnswer(): String {
        return this!!.text.toString()
    }

    fun setAnswer(text: String) {
        this.text = text
    }

    fun getSelecteds(): Boolean {
        return isSelected
    }

    fun setSelecteds(selected: Boolean) {
        isSelected = selected
    }
}