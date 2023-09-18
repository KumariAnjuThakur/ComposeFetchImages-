package com.example.topimagesdemoapp.retrofit

import com.example.topimagesdemoapp.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {
    companion object {
        const val BASE_URL = "https://api.imgur.com/3/gallery/search/top/week/"
        const val CLIENT_ID =""// copy your client ID here in Client-ID <ID> format
    }
    /**
     *
     * @param search The search query that is passed as Query string to the api
     * @param key The authorization key that has to be provided with this api
     * @return Observable<SearchResponse> the observable type of Rxjava
     */
//
    @GET("0")
    suspend fun getTopImagesOfTheWeek(@Query("q") search :String,
                                      @Header("Authorization") key:String): SearchResponse
}