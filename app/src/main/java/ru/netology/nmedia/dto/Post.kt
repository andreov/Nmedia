package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean,
    val countLike: Long,
    val countShare: Long,
    val urlVideo: String = ""
)