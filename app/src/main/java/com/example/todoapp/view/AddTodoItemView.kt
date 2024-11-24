package com.example.todoapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.todoapp.ApiResponse
import com.example.todoapp.MainViewModel
import com.example.todoapp.ui.theme.colorPrimary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddTodoItemView(viewModel: MainViewModel, navController: NavHostController) {
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
                        text = "Add todo items here",
                    )
                },
                navigationIcon = {
                    //do nothing
                },
            )
        },
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            AddTodoItem(viewModel)

            when (viewModel.getApiResponse()) {
                is ApiResponse.LOADING -> {
                    DisplayProgressIndicator()
                }

                is ApiResponse.SUCCESS -> {
                    navController.previousBackStackEntry?.savedStateHandle?.set("key", false)
                    navController.popBackStack()
                    viewModel.setApiResponse(ApiResponse.NONE)
                }

                is ApiResponse.ERROR -> {
                    navController.previousBackStackEntry?.savedStateHandle?.set("key", true)
                    navController.popBackStack()
                    viewModel.setApiResponse(ApiResponse.NONE)
                }

                else -> {
                    //do nothing
                }
            }
        }
    }
}

@Composable
internal fun DisplayProgressIndicator() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
            color = colorPrimary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddTodoItem(viewModel: MainViewModel) {
    val item = remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            value = item.value,
            onValueChange = {
                item.value = it
            },
            textStyle = TextStyle(fontSize = 17.sp),
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = null,
                    tint = Color.Gray
                )
            },
            placeholder = { Text(text = "Enter text") },
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.DarkGray
            ),
            maxLines = 1
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Add TODO",
                modifier = Modifier
                    .padding(top = 16.dp)
                    .background(color = colorPrimary, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
                    .clickable {
                        viewModel.insert(item.value)
                    }
            )
        }
    }
}
