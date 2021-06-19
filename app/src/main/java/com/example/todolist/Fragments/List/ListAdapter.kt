package com.example.todolist.Fragments.List

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.data.models.Priority
import com.example.todolist.data.models.TodoData
import kotlinx.android.synthetic.main.row_layout.view.*

class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    var dataList = emptyList<TodoData>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)

        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.title_txt.text = dataList[position].title
        holder.itemView.description_txt.text = dataList[position].description

        val action  = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
        holder.itemView.row_layout_indicator.setOnClickListener {
            holder.itemView.findNavController().navigate(action)

        }


        when (dataList[position].priority) {

            Priority.High -> holder.itemView.priority_indicator.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.red
                )
            )
            Priority.Medium -> holder.itemView.priority_indicator.setCardBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.yellow)
            )
            Priority.Low -> holder.itemView.priority_indicator.setCardBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.green
                )
            )


        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    fun setData(todoData: List<TodoData>){
        this.dataList = todoData
        notifyDataSetChanged()
    }
}