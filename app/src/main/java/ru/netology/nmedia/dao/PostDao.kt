package ru.netology.nmedia.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.netology.nmedia.entity.PostEntity

@Dao
interface PostDao {

    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Query(
        """
           UPDATE postentity SET
               countLike = countLike + CASE WHEN likedByMe THEN -1 ELSE 1 END,
               likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
           WHERE id = :id
        """)
    fun like(id:Long)

    @Query("""
           UPDATE PostEntity SET
               countShare = countShare + 1               
           WHERE id = :id
        """)
    fun share(id:Long)

    @Query("DELETE FROM PostEntity WHERE id = :id")
    fun remove(id:Long)

//    @Delete
//    fun remove(post: PostEntity)

    @Insert
    fun savePost(post: PostEntity)

    @Update
    fun editPost(post: PostEntity)
}