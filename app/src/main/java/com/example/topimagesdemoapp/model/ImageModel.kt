package com.example.topimagesdemoapp.model

data class ImageModel(
    var id: String? = null,
    var title: Any? = null,
    var description: Any? = null,
    var datetime: Long? = null,
    var type: String? = null,
    var animated: Boolean? = null,
    var width: Long? = null,
    var height: Long? = null,
    var size: Long? = null,
    var views: Long? = null,
    var bandwidth: Long? = null,
    var vote: Any? = null,
    var favorite: Boolean? = null,
    var nsfw: Any? = null,

    var section: Any? = null,
    var accountUrl: Any? = null,

    var accountId: Any? = null,

    var isAd: Boolean? = null,


    var hasSound: Boolean? = null,

    var tags: List<Any>? = null,

    var adType: Long? = null,

    var adUrl: String? = null,

    var edited: String? = null,

    var inGallery: Boolean? = null,

    var link: String? = null,

)