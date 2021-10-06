package com.dsc.todolist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), TodoAdapter.OnTodoItemClickedListener{

    private lateinit var adapter: TodoAdapter
    private lateinit var database: TodoListDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.setTitle("Task")

        val btnAdd = findViewById<FloatingActionButton>(R.id.btn_add)
        val rv = findViewById<RecyclerView>(R.id.mRecyclerView)

        adapter = TodoAdapter()
        database = TodoListDatabase.getInstance(this)!!
        adapter?.setTodoItemClickedListener(this)

        btnAdd.setOnClickListener {
            val intent =  Intent(this,TodoAdd::class.java)
            startActivity(intent)
        }
        val todos = mutableListOf<Todo>()
        rv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)

        }
        rv.adapter = adapter
        adapter.updateData(todos)

    }

    override fun onResume() {
        super.onResume()
        val newDatabase = database.getTodoDao().getTodoList()
        if (!newDatabase.isNullOrEmpty()){
            adapter.updateData(newDatabase)
        }
    }

    override fun onTodoItemClicked(todo: Todo) {
        val intent = Intent(this, TodoAdd::class.java)
        intent.putExtra("tId", todo.tId)
        intent.putExtra("title", todo.title)
        intent.putExtra("desc", todo.desc)
        intent.putExtra("date", todo.date)
        intent.putExtra("ischecked", todo.ischecked)
        startActivity(intent)

    }



}