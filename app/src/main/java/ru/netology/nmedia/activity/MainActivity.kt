package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity

                                     //def activity_version = "1.1.0"
import androidx.activity.viewModels //implementation "androidx.activity:activity-ktx:$activity_version"
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.observe
import kotlinx.android.synthetic.main.activity_main.*
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnInteractionListener
//import ru.netology.nmedia.adapter.OnLikeListener
//import ru.netology.nmedia.adapter.OnShareListener
//import kotlinx.android.synthetic.main.activity_main.*  // установка в Gradle
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.ShortFormatCount
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel:PostViewModel by viewModels()

        val adapter = PostAdapter (object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.editPost(post)
            }

            override fun onLike(post: Post) {
                viewModel.like(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.remove(post.id)
            }

            override fun onShare(post: Post) {
                viewModel.share(post.id)
            }
        })

        binding.postViewList.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) {
                return@observe
            }
            with(binding.textPost) {
                requestFocus()
                setText(post.content)
            }
        }

        binding.savePost.setOnClickListener {
            with(binding.textPost) {
                if (text.isNullOrBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        context.getString(R.string.error_empty_content),
                        Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                viewModel.changeContent(text.toString())
                viewModel.savePost()

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
            }
        }

    }
}