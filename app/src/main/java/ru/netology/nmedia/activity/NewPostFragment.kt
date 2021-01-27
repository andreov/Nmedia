package ru.netology.nmedia.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {


    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)

        val details = arguments?.getParcelable<Post>(FeedFragment.KEY_PARSE_DATA)
        binding.edit.setText(details?.content)
        binding.urlVideo.setText(details?.urlVideo)

        binding.ok.setOnClickListener {
            if (!binding.edit.text.isNullOrBlank()) {
                viewModel.changeContent(binding.edit.text.toString())
                viewModel.changeUrl(binding.urlVideo.text.toString())
                viewModel.savePost()
                findNavController().navigate(R.id.action_newPostFragment_to_feedFragment)
            } else {
                Snackbar.make(binding.root, R.string.error_empty_content, LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok) {
                        findNavController().navigate(R.id.action_newPostFragment_to_feedFragment)
                    }
                    .show()
            }
            AndroidUtils.hideKeyboard(requireView())
        }
        return binding.root
    }
}

