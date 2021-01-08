package ru.netology.nmedia.dto

data class Post(
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val likedByMe: Boolean,
    var countLike: Long,
    var countShare: Long,
    var urlVideo: String = ""
)