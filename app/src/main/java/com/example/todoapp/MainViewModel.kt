package com.example.todoapp

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.entities.TodoItemEntity
import com.example.todoapp.data.repository.TodoListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TodoListRepository
) : ViewModel() {

    private val searchText = mutableStateOf("")
    private val apiResponse: MutableState<ApiResponse> = mutableStateOf(ApiResponse.NONE)

    internal fun getSearchText() = searchText.value
    internal fun getApiResponse() = apiResponse.value

    internal fun insert(item: String) {
        apiResponse.value = ApiResponse.LOADING
        viewModelScope.launch {
            if (item == ERROR_TEXT) {
                delay(3000)
                apiResponse.value = ApiResponse.ERROR
            } else {
                repository.insert(TodoItemEntity(name = item))
                delay(3000)
                apiResponse.value = ApiResponse.SUCCESS
            }
        }
    }

    internal fun getTodoList() = repository.getTodoList()

    internal fun setApiResponse(response: ApiResponse) {
        apiResponse.value = response
    }

    internal fun setSearchText(text: String) {
        searchText.value = text
    }

    companion object {
        private const val ERROR_TEXT = "error"
    }
}