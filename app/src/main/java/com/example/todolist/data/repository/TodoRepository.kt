package com.example.todolist.data.repository

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.todolist.data.TodoDao
import com.example.todolist.data.models.TodoData

class TodoRepository(private val todoDao: TodoDao) {

    val getAllDatabase:LiveData<List<TodoData>> = todoDao.getAllData()
    val sortByHighPriority : LiveData<List<TodoData>> = todoDao.sortByHighPriority()
    val sortByLowPriority : LiveData<List<TodoData>> = todoDao.sortByLowPriority()


    suspend fun insertData(todoData: TodoData){

        todoDao.insert(todoData)
    }

    suspend fun updateData(todoData: TodoData){
        todoDao.update(todoData)
    }

    suspend fun deleteData(todoData: TodoData){
        todoDao.delete(todoData)
    }

    suspend fun deleteAll(){
        todoDao.deleteAll()
    }

      fun searchDatabase(searchQuery: String):LiveData<List<TodoData>> {
         return todoDao.searchDatabase(searchQuery)
     }


}