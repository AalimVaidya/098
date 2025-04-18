package com.example.kinjal

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var dbHelper: DBHelper
    private lateinit var taskList: ArrayList<Task>
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DBHelper(this)
        taskList = dbHelper.getAllTasks()
        adapter = TaskAdapter(taskList, dbHelper)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val inputTask = findViewById<EditText>(R.id.editTextTask)
        val btnAdd = findViewById<Button>(R.id.buttonAdd)

        btnAdd.setOnClickListener {
            val taskText = inputTask.text.toString()
            if (taskText.isNotEmpty()) {
                dbHelper.insertTask(taskText)
                taskList.clear()
                taskList.addAll(dbHelper.getAllTasks())
                adapter.notifyDataSetChanged()
                inputTask.text.clear()
            }
        }
    }
}
