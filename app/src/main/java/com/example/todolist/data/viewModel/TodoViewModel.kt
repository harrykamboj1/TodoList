package com.example.todolist.data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import com.example.todolist.data.TodoDatabase
import com.example.todolist.data.models.TodoData
import com.example.todolist.data.repository.TodoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TodoViewModel(application: Application) : AndroidViewModel(application){

    private val todoDao = TodoDatabase.getDatabase(application).todoDao()

    private val repository : TodoRepository

     val getAllData : LiveData<List<TodoData>>
     val sortByHighPriority : LiveData<List<TodoData>>
     val sortByLowPriority : LiveData<List<TodoData>>

    init {
        repository  = TodoRepository(todoDao)
              getAllData = repository.getAllDatabase

        sortByHighPriority = repository.sortByHighPriority
         sortByLowPriority = repository.sortByLowPriority
    }

    fun insert(todoData: TodoData){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.insertData(todoData)
            }
        }
    }

    fun update(todoData: TodoData){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.updateData(todoData)
            }
        }
    }


    fun delete(todoData: TodoData){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.deleteData(todoData)
            }
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                repository.deleteAll()
            }
        }
    }

    fun searchDatabase(searchQuery: String):LiveData<List<TodoData>>{
       return repository.searchDatabase(searchQuery)
    }
}