package com.dsc.todolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton


class TodoAdapter:RecyclerView.Adapter<TodoAdapter.TodoViewHolder>(){

    private var onTodoItemClickedListener: OnTodoItemClickedListener?= null

    private val item = mutableListOf<Todo>()
    class TodoViewHolder(val view: View):RecyclerView.ViewHolder(view){
        fun bindItem(item: Todo) = with(view){

            val cbList = findViewById<CheckBox>(R.id.cbList)
            val txtTitle = findViewById<TextView>(R.id.txtTitle)
            val txtDesc = findViewById<TextView>(R.id.txtDesc)
            val txtDate = findViewById<TextView>(R.id.txtDate)

            cbList.isChecked = item.ischecked
            txtTitle.text = item.title
            txtDesc.text = item.desc
            txtDate.text = item.date

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false)
        return TodoViewHolder(v)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.view.setOnClickListener { onTodoItemClickedListener!!.onTodoItemClicked(item[position]) }

        holder.bindItem(item[position])
    }

    override fun getItemCount() = item.size

    fun updateData (newItems:MutableList<Todo>){
        item.clear()
        item.addAll(newItems)
        notifyDataSetChanged()
    }

    fun setTodoItemClickedListener(onTodoItemClickedListener: OnTodoItemClickedListener){
        this.onTodoItemClickedListener = onTodoItemClickedListener
    }

    interface OnTodoItemClickedListener{
        fun onTodoItemClicked(todo: Todo)

    }


}