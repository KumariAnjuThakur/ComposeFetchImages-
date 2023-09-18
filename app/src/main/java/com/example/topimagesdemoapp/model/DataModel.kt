package com.example.topimagesdemoapp.model

data class DataModel(

    var id: String? = null,
    var title: String? = null,
    var description: Any? = null,
    var datetime: Long? = null,
    var link: String? = null,
    var imagesCount: Long? = null,
    var images: List<ImageModel>? = null
)