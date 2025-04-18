package com.example.kinjal

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, "tasks.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE tasks(id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, isDone INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS tasks")
        onCreate(db)
    }

    fun insertTask(title: String) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("title", title)
        values.put("isDone", 0)
        db.insert("tasks", null, values)
    }

    fun getAllTasks(): ArrayList<Task> {
        val tasks = ArrayList<Task>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM tasks", null)
        if (cursor.moveToFirst()) {
            do {
                val task = Task(
                    id = cursor.getInt(0),
                    title = cursor.getString(1),
                    isDone = cursor.getInt(2) == 1
                )
                tasks.add(task)
            } while (cursor.moveToNext())
        }
        cursor.close()
        return tasks
    }

    fun updateTask(task: Task) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("isDone", if (task.isDone) 1 else 0)
        db.update("tasks", values, "id=?", arrayOf(task.id.toString()))
    }

    fun deleteTask(id: Int) {
        val db = writableDatabase
        db.delete("tasks", "id=?", arrayOf(id.toString()))
    }
}
