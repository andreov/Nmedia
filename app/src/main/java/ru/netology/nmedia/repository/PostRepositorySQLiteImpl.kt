package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post

class PostRepositorySQLiteImpl (private val dao: PostDao) : PostRepository {

    private var posts = emptyList<Post>()
    private val data= MutableLiveData(posts)

    init {
        posts = dao.getAll()
        data.value= posts
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun savePost(post: Post) {
        val id=post.id
        val saved= dao.savePost(post)
        posts = if (id == 0L) {
            listOf(saved) + posts
        } else {
            posts.map {
                if ( it.id != id) it else saved
            }
        }
        data.value = posts
    }

    override fun like(id: Long) {
        dao.like(id)
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                countLike = if (it.likedByMe) it.countLike - 1 else it.countLike + 1
            )
        }
        data.value = posts
    }

    override fun remove(id: Long) {
        dao.remove(id)
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun share(id: Long) {
        dao.share(id)
        posts = posts.map {
            if (it.id != id) it else it.copy(
                countShare = it.countShare + 1
            )
        }
        data.value = posts
    }

}