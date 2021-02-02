package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.activity.FeedFragment.Companion.KEY_PARSE_DATA
import ru.netology.nmedia.databinding.FragmentEditBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.viewmodel.PostViewModel


class EditFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentEditBinding.inflate(inflater, container, false)

        val bundlePost = arguments?.getParcelable<Post>(KEY_PARSE_DATA)
        binding.editContent.text = bundlePost?.content
        binding.editAuthor.text = bundlePost?.author
        binding.editPublished.text = bundlePost?.published
        binding.editUrlText.text = bundlePost?.urlVideo
        viewModel.changeId()

        binding.menu.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.options_post)           // пункты меню
                setOnMenuItemClickListener { item ->  // обработчик клика пункта меню
                    when (item.itemId) {
                        R.id.remove -> {
                            bundlePost?.let { it1 -> viewModel.remove(it1.id) }
                            findNavController().navigateUp()
                            true

                        }
                        R.id.edit -> {
                            if (bundlePost != null) {
                                viewModel.editPost(bundlePost)
                                findNavController().navigate(
                                    R.id.action_editFragment_to_newPostFragment2,
                                    FeedFragment.bundle
                                )
                            }
                            true
                        }
                        else -> false
                    }
                }
            }.show()  //показ меню
        }
        return binding.root
    }


}