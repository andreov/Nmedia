package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
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

    private val newPostRequestCode = 1

    //private val editPostRequestCode = 2
    private val viewModel: PostViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val viewModel:PostViewModel by viewModels()

        val adapter = PostAdapter (object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.editPost(post)
                //val intent = Intent(this@MainActivity, NewPost::class.java)
            }

            override fun onLike(post: Post) {
                viewModel.like(post.id)
            }

            override fun onRemove(post: Post) {
                viewModel.remove(post.id)
            }
            // неявный интент - отправка текста в сообщение чата
            override fun onShare(post: Post) {
                viewModel.share(post.id)
                val intent= Intent().apply{
                    action= Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, post.content)
                    type="text/plain"
                }
                val shareIntent= Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent)

            }
        })

        binding.postViewList.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener {
            val intent = Intent(this@MainActivity, NewPost::class.java)
            val bundle = Bundle()
            bundle.putBoolean("key2", false)
            intent.putExtras(bundle)
            startActivityForResult(intent, newPostRequestCode)
            //startActivity(intent)
        }



        viewModel.edited.observe(this) { post ->
            if (post.id == 0L) {
                return@observe
            }
            val intent = Intent(this@MainActivity, NewPost::class.java)
            val bundle = Bundle()
            bundle.putString("key1", post.content)
            bundle.putString("key3", post.urlVideo)
            bundle.putBoolean("key2", true)
            intent.putExtras(bundle)
            startActivityForResult(intent, newPostRequestCode)
        }



    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            newPostRequestCode -> {
                if (resultCode != Activity.RESULT_OK) {
                    return
                }

                data?.getStringExtra(Intent.EXTRA_TEXT)?.let {
                    viewModel.changeContent(it)
                    //viewModel.savePost()
                }

                data?.getStringExtra(Intent.EXTRA_HTML_TEXT)?.let {
                    viewModel.changeUrl(it)
                    //viewModel.savePost()
                }
                viewModel.savePost()
            }

        }
    }
}