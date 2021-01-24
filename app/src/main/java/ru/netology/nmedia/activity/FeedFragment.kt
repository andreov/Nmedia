package ru.netology.nmedia.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.FeedFragment.Companion.EDIT_URL_KEY
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.adapter.PostAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.dto.Post

class FeedFragment : Fragment() {

    companion object {

        const val EDIT_CONTENT_KEY = "key1"
        const val IS_UPDATE_POST_KEY = "key2"
        const val EDIT_URL_KEY = "key3"
        //var bandle = Bundle()//.arguments: String?
//              set(value) = putString(EDIT_CONTENT_KEY, value)
//              get() = getString(EDIT_CONTENT_KEY)
    }



    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentFeedBinding.inflate(inflater, container,false)

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

            // неявный интент - отправка video в yutube
            override fun onVideo(post: Post) {
                val url:String=post.urlVideo
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)

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
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)

        }

        viewModel.edited.observe(viewLifecycleOwner) { post ->
            if (post.id == 0L) {
                return@observe
            }
            //val textContent: String? = .getStringExtra(Intent.EXTRA_TEXT)

//            val intent = Intent(this@FeedFragment, NewPostFragment::class.java)
            val bundle = Bundle()
            bundle.putString(EDIT_CONTENT_KEY, post.content)
            bundle.putString(EDIT_URL_KEY, post.urlVideo)
            bundle.putBoolean(IS_UPDATE_POST_KEY, true)
           findNavController().navigate(R.id.action_feedFragment_to_newPostFragment, bundle)
//            intent.putExtras(bundle)
//            startActivityForResult(intent, newPostRequestCode)
        }

        return binding.root
    }

}