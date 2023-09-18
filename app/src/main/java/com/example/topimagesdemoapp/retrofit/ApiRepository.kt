package com.example.topimagesdemoapp.retrofit

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiService: ApiService) {
    fun getTopImagesOfTheWeek(query :String) = flow {
        Log.i("ApiRepository","getTopImagesOfTheWeek for query $query")
        emit(apiService.getTopImagesOfTheWeek(query,ApiService.CLIENT_ID))
    }.flowOn(Dispatchers.IO)

}