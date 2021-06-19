package com.example.todolist.data

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import com.example.todolist.R
import com.example.todolist.data.models.Priority
import java.util.*

class ShareTodoViewModel(application: Application) : AndroidViewModel(application) {


    val listener: AdapterView.OnItemSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when(position){
                0 ->{
                    (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,
                        R.color.red))}
                1 ->{
                    (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,
                        R.color.yellow))}
                2 ->{
                    (parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,
                        R.color.green))}
            }
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {


        }


    }

     fun verifyDataFromUser(title:String,description:String):Boolean{
        return  if(TextUtils.isEmpty(title) || TextUtils.isEmpty(description)){
            false
        }else !(title.isEmpty() || description.isEmpty())
    }

     fun parsePriority(priority:String): Priority {
        return when(priority){
            "High Priority" -> {
                Priority.High}
            "Medium Priority" -> {
                Priority.Medium}
            "Low Priority" -> {
                Priority.Low}
            else -> Priority.Low
        }
    }

     fun parsePriority1(priority: Priority): Int {
        return when(priority){
            Priority.High->0
            Priority.Medium->1
            Priority.Low->2
        }
    }

}