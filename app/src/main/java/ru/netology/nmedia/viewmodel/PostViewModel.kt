package ru.netology.nmedia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryInMemoryImpl

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

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
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

    fun changeUrl(content: String) {   // изменение контента поста
        val text = content.trim()
        if (edited.value?.urlVideo == text) {
            return
        }
        edited.value = edited.value?.copy(urlVideo = text)
    }
}