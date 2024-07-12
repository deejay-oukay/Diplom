package com.example.tmiz.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import android.widget.Toast
import com.example.tmiz.R
import java.util.ArrayList

class QuestionAdapter(private val context: Context, private var modelArrayList: ArrayList<QuestionsModel>
) : BaseAdapter() {
    override fun getViewTypeCount(): Int = count
    override fun getItemViewType(position: Int): Int = position
    override fun getCount(): Int = modelArrayList.size
    override fun getItem(position: Int): Any = modelArrayList[position]
    override fun getItemId(position: Int): Long = 0
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder
        if (convertView == null)
        {
            holder = ViewHolder()
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.answers, null, true)
            holder.id = convertView!!.findViewById(R.id.question_id)!!
            holder.text = convertView.findViewById(R.id.question_text)!!
            convertView.tag = holder
        }
        else
            holder = convertView.tag as ViewHolder
        holder.text!!.text = modelArrayList[position].getText()
        holder.id!!.text = modelArrayList[position].getId()
        holder.id!!.setTag(R.integer.btn_plus_view, convertView)
        holder.id!!.tag = position
        holder.id!!.setOnClickListener {
            Toast.makeText(context,
                "Загрузка статистики пока не реализована: ", Toast.LENGTH_SHORT).show()
        }
        return convertView
    }
    private inner class ViewHolder {
        var id: TextView? = null
        var text: TextView? = null
    }
    companion object {
        var questionsList: ArrayList<QuestionsModel> = arrayListOf()
    }
}