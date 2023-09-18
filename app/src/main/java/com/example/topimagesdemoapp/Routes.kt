package com.example.topimagesdemoapp

sealed class Routes(val route: String) {
    object TopImages : Routes("TopImages")

}
