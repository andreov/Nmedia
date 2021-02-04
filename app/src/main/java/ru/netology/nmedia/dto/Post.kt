package ru.netology.nmedia.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Post(
    val id: Long,
    var author: String,
    val content: String,
    var published: String,
    val likedByMe: Boolean,
    val countLike: Long = 0,
    val countShare: Long = 0,
    val urlVideo: String = ""
):Parcelable