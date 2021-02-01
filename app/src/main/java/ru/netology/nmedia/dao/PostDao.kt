package ru.netology.nmedia.dao

import ru.netology.nmedia.dto.Post

interface PostDao {
    fun getAll(): List<Post>
    fun like(id:Long)
    fun share(id:Long)
    fun remove(id:Long)
    fun savePost(post: Post) : Post
}