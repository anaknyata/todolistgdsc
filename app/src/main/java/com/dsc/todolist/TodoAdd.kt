package com.dsc.todolist

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.SyncStateContract.Helpers.update
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.row_layout.*
import java.util.*


class TodoAdd : AppCompatActivity(), DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    private var todoDatabase: TodoListDatabase? = null

    private var day = 0
    private var month = 0
    private var year = 0
    private var hour = 0
    private var minute = 0

    private var savedDay = 0
    private var savedMonth = 0
    private var savedYear = 0
    private var savedHour = 0
    private var savedMinute = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)


        val etTitle = findViewById<EditText>(R.id.etTitle)
        val etDesc = findViewById<EditText>(R.id.etDesc)
        val etDate = findViewById<TextView>(R.id.etDate)
        val btnSave = findViewById<Button>(R.id.btnSave)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        todoDatabase = TodoListDatabase.getInstance(this)

        val title = intent.getStringExtra("title")
        val desc = intent.getStringExtra("desc")
        val date = intent.getStringExtra("date")

        pickDate()

        if (title == null || title == "" ){
            setTitle("New Task")
            btnSave.setOnClickListener{
                val todo =
                    Todo(
                            title = etTitle.text.toString(),
                            desc = etDesc.text.toString(),
                            date = etDate.text.toString(),
                            ischecked = false
                            )
                todoDatabase!!.getTodoDao().saveTodo(todo)
                finish()
            }
        } else {
            setTitle("Detail")
            btnSave.text = getString(R.string.update)
            val tId = intent.getIntExtra("tId", 0)
            etTitle.setText(title)
            etDesc.setText(desc)
            etDate.setText(date)
            btnSave.setOnClickListener {
                val todo =
                        Todo(
                                title = etTitle.text.toString(),
                                desc = etDesc.text.toString(),
                                date = etDate.text.toString(),
                                ischecked = false,
                                tId
                        )
                todoDatabase!!.getTodoDao().updateTodo(todo)
                finish()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item?.itemId == android.R.id.home){
            startActivity(Intent(Intent(this, MainActivity::class.java)))
        }
        if (item?.itemId == R.id.btnDelete){
            deleteItem()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val title = intent.getStringExtra("title")
        if (title !== null){
            menuInflater.inflate(R.menu.menu_deletetodo, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun deleteItem(){
        val etTitle = findViewById<EditText>(R.id.etTitle)
        val etDesc = findViewById<EditText>(R.id.etDesc)
        val etDate = findViewById<TextView>(R.id.etDate)
        val tId = intent.getIntExtra("tId", 0)
        val todo =
                Todo(
                        title = etTitle.text.toString(),
                        desc = etDesc.text.toString(),
                        date = etDate.text.toString(),
                        ischecked = false,
                        tId
                )
        todoDatabase!!.getTodoDao().deleteTodo(todo)
        finish()
    }

    private fun getDateTimeCalendar() {
        val cal = Calendar.getInstance()
        day = cal.get(Calendar.DAY_OF_MONTH)
        month = cal.get(Calendar.MONTH)
        year = cal.get(Calendar.YEAR)
        hour = cal.get(Calendar.HOUR)
        minute  = cal.get(Calendar.MINUTE)
    }

    private fun pickDate() {
        val btnDate = findViewById<ImageButton>(R.id.btnDate)
        btnDate.setOnClickListener {
            getDateTimeCalendar()

            DatePickerDialog(this, this, year, month ,day).show()
        }
    }

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        savedDay = dayOfMonth
        savedMonth = month
        savedYear = year

        getDateTimeCalendar()

        TimePickerDialog(this,this,hour,minute,true).show()
    }

    @SuppressLint("SetTextI18n")
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        savedHour = hourOfDay
        savedMinute = minute

        etDate.text = "$savedDay/$savedMonth/$savedYear/$savedHour/$savedMinute"
    }


}