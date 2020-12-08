package ru.netology.nmedia.activity

import androidx.appcompat.app.AppCompatActivity

                                     //def activity_version = "1.1.0"
import androidx.activity.viewModels //implementation "androidx.activity:activity-ktx:$activity_version"
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.observe
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.OnLikeListener
import ru.netology.nmedia.adapter.OnShareListener
//import kotlinx.android.synthetic.main.activity_main.*  // установка в Gradle
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.dto.ShortFormatCount
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.adapter.PostAdapter

class MainActivity : AppCompatActivity() {


    //private val shortFormatCount = ShortFormatCount()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel:PostViewModel by viewModels()

        val adapter = PostAdapter ({
            viewModel.like(it.id)},
            {viewModel.share(it.id)
        })

        binding.postViewList.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.list = posts
        }
//        viewModel.data.observe(this) { post ->
//            with(binding) {
//                author.text = post.author
//                published.text = post.published
//                content.text = post.content
//                textLike.text = shortFormatCount.countFormat(post.countLike)
//                textShare.text = shortFormatCount.countFormat(post.countShare)
//                imageLike.setImageResource(
//                    if(post.likedByMe) R.drawable.ic_baseline_favorite_24 else R.drawable.ic_baseline_favorite_border_24
//                )
//            }
//        }
//        binding.imageLike.setOnClickListener{
//            viewModel.like()
//        }
//
//        binding.imageShare.setOnClickListener{
//            viewModel.share()
//        }

    }
}