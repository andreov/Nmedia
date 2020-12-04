package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.dto.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var post = Post(
        id = 1,
        author = "Нетология. Университет интернет-профессий будущего",
        content = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        published = "2 декабря в 22:40",
        likedByMe = false,
        countLike = 999L,
        countShare = 1099L
    )

    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data

    override fun like() {
        val likedByMe:Boolean=!post.likedByMe
        val countLike:Long = if(likedByMe) post.countLike+1 else post.countLike-1
        post = post.copy(likedByMe = likedByMe, countLike = countLike)
        data.value = post
    }
    override fun share(){
        post = post.copy(countShare = post.countShare+1)
        data.value = post
    }

}