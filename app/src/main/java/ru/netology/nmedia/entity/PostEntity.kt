package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.dto.Post

@Entity
data class PostEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val author: String, // ="AndreOv",
    val content: String,
    val published: String, // = "12 december 10:12",
    val likedByMe: Boolean,
    val countLike: Long = 0,
    val countShare: Long = 0,
    val urlVideo: String = ""
) {
    fun toDto() = Post(id, author, content, published, likedByMe, countLike, countShare, urlVideo)

    companion object {
        fun fromDto(dto: Post) =
            PostEntity(dto.id, dto.author, dto.content, dto.published, dto.likedByMe, dto.countLike, dto.countShare, dto.urlVideo)

    }
}