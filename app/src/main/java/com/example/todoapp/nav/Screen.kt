package com.example.todoapp.nav

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object AddItemScreen : Screen("add_item_screen")
}