package com.example.tmiz.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import com.example.tmiz.R
import java.util.ArrayList

class CustomAdapter1(private val context: Context,
                     private var modelArrayList: ArrayList<AnswersModel>
) : BaseAdapter() {
    override fun getViewTypeCount(): Int {
        return count
    }
    override fun getItemViewType(position: Int): Int {
        return position
    }
    override fun getCount(): Int {
        return modelArrayList.size
    }
    override fun getItem(position: Int): Any {
        return modelArrayList[position]
    }
    override fun getItemId(position: Int): Long {
        return 0
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder
        if (convertView == null)
        {
            holder = ViewHolder()
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = inflater.inflate(R.layout.answers1, null, true)
            holder.check = convertView!!.findViewById(R.id.answer_check)!!
            holder.label = convertView.findViewById(R.id.answer_label)!!
            convertView.tag = holder
        }
        else
            holder = convertView.tag as ViewHolder
        holder.label!!.text = modelArrayList[position].getAnswer()
        holder.check!!.isChecked = modelArrayList[position].getSelecteds()
        holder.check!!.setTag(R.integer.btn_plus_view, convertView)
        holder.check!!.tag = position
        holder.check!!.setOnClickListener {
            val pos = holder.check!!.tag as Int
            Toast.makeText(context,
                context.getString(R.string.toast_selected_answers_number)+(pos+1), Toast.LENGTH_SHORT).show()
            for (i in 0..<modelArrayList.size)
                if (i != pos)
                    modelArrayList[i].setSelecteds(false)
            if (modelArrayList[pos].getSelecteds()) {
                modelArrayList[pos].setSelecteds(false)
                answersList = modelArrayList
            }
            else
            {
                modelArrayList[pos].setSelecteds(true)
                answersList = modelArrayList
            }
        }
        return convertView
    }
    private inner class ViewHolder {
        var check: RadioButton? = null
        var label: TextView? = null
    }
    companion object {
        lateinit var answersList: ArrayList<AnswersModel>
    }

}