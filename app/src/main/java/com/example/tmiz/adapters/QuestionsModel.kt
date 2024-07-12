package com.example.tmiz.adapters

class QuestionsModel {
    private var id: String? = null
    private var text: String? = null
    fun getId(): String {
        return this!!.id.toString()
    }
    fun getText(): String {
        return this!!.text.toString()
    }
    fun setId(id: String) {
        this.id = id
    }
    fun setText(text: String) {
        this.text = text
    }
}