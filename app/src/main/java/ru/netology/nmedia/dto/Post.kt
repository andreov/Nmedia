package ru.netology.nmedia.dto

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.netology.nmedia.R

@Parcelize
data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean,
    val countLike: Long = 0,
    val countShare: Long = 0,
    val urlVideo: String = ""
):Parcelable