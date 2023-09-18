package com.example.topimagesdemoapp.component

import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.SnackbarDuration
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun ShowSnackBar(message: String) {
    val scaffoldState: ScaffoldState = rememberScaffoldState()
    LaunchedEffect(Unit) {
        scaffoldState.snackbarHostState.showSnackbar(
            message = message,
            duration = SnackbarDuration.Short
        )

    }
    Scaffold(scaffoldState = scaffoldState, content = {})

}