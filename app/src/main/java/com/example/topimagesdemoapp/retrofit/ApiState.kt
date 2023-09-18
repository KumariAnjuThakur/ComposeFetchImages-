package com.example.topimagesdemoapp.retrofit


sealed class ApiState{
    class Success() : ApiState()
    class Failure(val msg: String) : ApiState()
    object Loading:ApiState()
    object Empty: ApiState()
}
