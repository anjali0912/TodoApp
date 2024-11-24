@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.todoapp.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.todoapp.MainViewModel
import com.example.todoapp.data.entities.TodoItemEntity
import com.example.todoapp.nav.Screen
import com.example.todoapp.ui.theme.colorPrimary
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainView(viewModel: MainViewModel, navController: NavHostController) {
    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            modifier = Modifier.background(Color.Green),
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = colorPrimary,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    ),
                    title = {
                        Text(
                            text = "Todo items list",
                        )
                    },
                    navigationIcon = {
                        //do nothing
                    },
                )
            },
        ) { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                val todoList = viewModel.getTodoList().collectAsState(initial = emptyList())

                if (todoList.value.isEmpty()) {
                    DisplayTextToAddTodo()
                } else {
                    DisplayTodoList(todoList.value, viewModel)
                }
                DisplayFloatingButton(navController)
            }
        }
        if (navController.currentBackStackEntry?.savedStateHandle?.contains("key") == true) {
            DisplayBanner()
        }
    }
}

@Composable
internal fun DisplayFloatingButton(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 24.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        FloatingActionButton(
            elevation = FloatingActionButtonDefaults.elevation(10.dp),
            shape = CircleShape,
            containerColor = colorPrimary,
            onClick = {
                navController.navigate(Screen.AddItemScreen.route)
            },
        ) {
            Icon(Icons.Filled.Add, "Floating action button.")
        }
    }
}

@Composable
internal fun DisplayTodoList(todoList: List<TodoItemEntity>, viewModel: MainViewModel) {
    Column {
        CustomSearchView(viewModel = viewModel)
        LazyColumn {
            val list = todoList.filter {
                it.name.contains(viewModel.getSearchText())
            }

            items(list) { item ->
                Text(
                    text = item.name.uppercase(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp, horizontal = 20.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.W500
                    )
                )

                HorizontalDivider()
            }
        }
    }
}

@Composable
internal fun DisplayTextToAddTodo() {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Press the + button to add a TODO item",
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
internal fun CustomSearchView(viewModel: MainViewModel) {
    Box(
        modifier = Modifier
            .padding(20.dp)
            .clip(CircleShape)
            .background(Color(0XFF101921))

    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1,
            value = viewModel.getSearchText(),
            onValueChange = {
                viewModel.setSearchText(it)
            },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.DarkGray
            ),
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "") },
            placeholder = { Text(text = "Search") }
        )
    }
}

@Composable
private fun DisplayBanner() {
    val show = remember { mutableStateOf(true) }

    LaunchedEffect(key1 = Unit) {
        delay(3000)
        show.value = false
    }
    if (show.value) {
        Card(
            modifier = Modifier
                .padding(top = 50.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(2.dp),
            border = BorderStroke(width = 2.dp, color = colorPrimary),
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(all = 10.dp),
                text = "Failed to add TODO",
                style = TextStyle(
                    fontWeight = FontWeight.W700,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = colorPrimary
                )
            )
        }
    }
}