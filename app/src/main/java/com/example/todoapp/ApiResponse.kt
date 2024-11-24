package com.example.todoapp

sealed class ApiResponse {
    data object NONE : ApiResponse()
    data object LOADING : ApiResponse()
    data object SUCCESS : ApiResponse()
    data object ERROR : ApiResponse()
}