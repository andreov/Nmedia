package ru.netology.nmedia.repository

import androidx.lifecycle.Transformations
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity

class PostRepositorySQLiteImpl(private val dao: PostDao) : PostRepository {

    override fun getAll() = Transformations.map(dao.getAll()) { list ->
        list.map { entity ->
            entity.toDto()
        }
    }

    override fun savePost(post: Post) {
        if (post.id == 0L) {
            post.author = "AndreOv"
            post.published = "12 december 10:12"
            dao.savePost(PostEntity.fromDto(post))
        } else dao.editPost(PostEntity.fromDto(post))

    }

    override fun like(id: Long) {
        dao.like(id)
    }

    override fun remove(id: Long) {
        dao.remove(id)
    }

    override fun share(id: Long) {
        dao.share(id)
    }

}