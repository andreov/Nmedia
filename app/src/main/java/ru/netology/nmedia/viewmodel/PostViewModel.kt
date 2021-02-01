package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryFileImpl
import ru.netology.nmedia.repository.PostRepositorySQLiteImpl

//import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl
//import ru.netology.nmedia.repository.PostRepositorySharedPrefsImpl

private val empty = Post(   //data post для заполнения нового поста
    id = 0,
    author = "",
    content = "",
    published = "",
    likedByMe = false,
    countShare = 0,
    countLike = 0,
    urlVideo = ""
)

class PostViewModel (application: Application) : AndroidViewModel(application) {
    //private val repository: PostRepository = PostRepositoryFileImpl(application)  //PostRepositorySharedPrefsImpl(application)
    private val repository: PostRepository = PostRepositorySQLiteImpl(
        AppDb.getInstance(application).postDao
    )
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun like(id: Long) = repository.like(id)
    fun share(id: Long) = repository.share(id)
    fun remove(id: Long) = repository.remove(id)


    fun savePost() {                 //сохранение поста
        edited.value?.let {
            repository.savePost(it)
        }
        edited.value = empty
    }

    fun changeId() {
        edited.value = empty
    }

    fun editPost(post: Post) {
        edited.value = post
    }

    fun changeContent(content: String) {   // изменение контента поста
        val text = content.trim()
        if (edited.value?.content == text) {
            return
        }
        edited.value = edited.value?.copy(content = text)
    }

    fun changeUrl(content: String) {   // изменение url video
        val text = content.trim()
        if (edited.value?.urlVideo == text) {
            return
        }
        edited.value = edited.value?.copy(urlVideo = text)
    }
}