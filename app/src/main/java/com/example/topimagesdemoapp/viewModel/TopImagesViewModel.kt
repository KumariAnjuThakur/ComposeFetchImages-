package com.example.topimagesdemoapp.viewModel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.topimagesdemoapp.model.DataModel
import com.example.topimagesdemoapp.model.ImageModel
import com.example.topimagesdemoapp.model.SearchResponse
import com.example.topimagesdemoapp.retrofit.ApiRepository
import com.example.topimagesdemoapp.retrofit.ApiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TopImagesViewModel @Inject constructor() : ViewModel() {
    val _tag = "TopImagesViewModel"

    @Inject
    lateinit var apiRepository: ApiRepository
    val response: MutableState<ApiState> = mutableStateOf(ApiState.Empty)

    val isViewTypeListState = mutableStateOf(true)

    val searchResultsState = MutableStateFlow<List<DataModel>>(listOf())

    private val _searchtext = MutableStateFlow("")
    val searchText = _searchtext.asStateFlow()

    fun onSearchTextChange(searchText: String) {
        if (searchText.isEmpty())
            response.value = ApiState.Empty
        _searchtext.value = searchText

    }

    fun postSearchQuery(isInternet: Boolean) {
        viewModelScope.launch(Dispatchers.Default) {
            if (isInternet) {
                val search = searchText.value
                if (search.isNotEmpty()) {
                    Log.i(_tag, "Request Images for  $search")
                    apiRepository.getTopImagesOfTheWeek(search).onStart {
                        response.value = ApiState.Loading

                    }.catch {
                        response.value = ApiState.Failure("")
                    }.collectLatest {
                        handleSearchResponse(it)
                    }
                } else
                    response.value = ApiState.Empty

            } else {
                response.value = ApiState.Failure("")
            }

        }
    }


    private fun handleSearchResponse(searchResponse: SearchResponse) {
        if (searchText.value.isNotEmpty()) {
            val searchResultList = searchResponse.data
            val searchResults = mutableListOf<DataModel>()
            if (!searchResultList.isNullOrEmpty()) {
                for (searchResult in searchResultList) {
                    val images = searchResult.images
                    val imageList = mutableListOf<ImageModel>()
                    if (!images.isNullOrEmpty()) {
                        for (image in images) {
                            val imageLink = image.link
                            if (!imageLink.isNullOrEmpty()) {
                                imageList.add(image)
                            }
                        }
                    }
                    searchResult.images = imageList
                    searchResults.add(searchResult)
                }
            }
            searchResultsState.value = searchResults
            response.value = ApiState.Success()
        } else
            response.value = ApiState.Empty

    }

}