package ru.netology.nmedia.repository

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.dto.Post


class PostRepositorySharedPrefsImpl(context: Context) : PostRepository {

    companion object {
        const val POST_FILE = "repo"
        const val POST_KEY = "posts"
        }

    private val gson = Gson()
    //ссылка на preferens, передается имя файла
    private val prefs = context.getSharedPreferences(POST_FILE, Context.MODE_PRIVATE)
    // для работы gson со списком
    private val type = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private var nextId = 1L
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        prefs.getString(POST_KEY, null)?.let {
            posts = gson.fromJson(it, type)
            data.value = posts
        }
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun savePost(post: Post) {
        if (post.id == 0L) {
            posts = listOf(
                post.copy(
                    id = nextId++,
                    author = "AndreOv",
                    likedByMe = false,
                    published = "12 december 10:12"
                )
            ) + posts
            data.value = posts
            sync()
            return
        }
        posts = posts.map {
            if (it.id != post.id) it else it.copy(content = post.content, urlVideo = post.urlVideo)
        }
        data.value = posts
        sync()

    }

    override fun like(id: Long) {
        posts = posts.map {
            if (it.id != id) it else {
                val likedByMe: Boolean = !it.likedByMe
                it.copy(
                    likedByMe = likedByMe,
                    countLike = if (likedByMe) it.countLike + 1 else it.countLike - 1
                )
            }
        }
        data.value = posts
        sync()
    }

    override fun share(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(countShare = it.countShare + 1)
        }
        data.value = posts
        sync()
    }

    override fun remove(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
        sync()
    }

    private fun sync() {
        with(prefs.edit()) {
            putString(POST_KEY, gson.toJson(posts))
            apply()
        }
    }


}