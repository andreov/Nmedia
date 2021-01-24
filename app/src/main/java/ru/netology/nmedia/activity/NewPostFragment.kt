package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
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
import ru.netology.nmedia.util.AndroidUtils
import ru.netology.nmedia.viewmodel.PostViewModel

class NewPostFragment : Fragment() {

//    companion object {
//
//       const val EDIT_CONTENT_KEY = "key1"
//        const val IS_UPDATE_POST_KEY = "key2"
//        const val EDIT_URL_KEY = "key3"
//        var bandle = Bundle()//.arguments: String?
////              set(value) = putString(EDIT_CONTENT_KEY, value)
////              get() = getString(EDIT_CONTENT_KEY)
//    }

    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)

        val isUpdatePost = arguments?.getBoolean(FeedFragment.IS_UPDATE_POST_KEY, false) == true

        if(isUpdatePost){
            val editContent = arguments?.getString(FeedFragment.EDIT_CONTENT_KEY)
            binding.edit.setText(editContent)
            val editUrl = arguments?.getString(FeedFragment.EDIT_URL_KEY)
            binding.urlVideo.setText(editUrl)

        }
        else {
            binding.edit.setText("")
            binding.edit.requestFocus()
        }

        //val editContent = arguments?.getString(FeedFragment.EDIT_CONTENT_KEY)
        //binding.edit.setText(editContent)
        //val editUrl = arguments?.getString(FeedFragment.EDIT_URL_KEY)
        //binding.urlVideo.setText(editUrl)


        binding.ok.setOnClickListener {
            if (!binding.edit.text.isNullOrBlank()){
                viewModel.changeContent(binding.edit.text.toString())
                viewModel.changeUrl(binding.urlVideo.text.toString())
                viewModel.savePost()
                Bundle().clear()
                findNavController().navigateUp()
            } else {
                Snackbar.make(binding.root, R.string.error_empty_content, LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok) {
                        findNavController().navigateUp()
                    }
                    .show()


            }

            AndroidUtils.hideKeyboard(requireView())
//            if (binding.edit.text.isNullOrBlank()) {
//                Snackbar.make(binding.root, R.string.error_empty_content, LENGTH_INDEFINITE)
//                    .setAction(android.R.string.ok) {
//                        setResult(Activity.RESULT_CANCELED, intent)
//                        finish()
//                    }
//                    .show()
//
//            } else {
//                val content = binding.edit.text.toString()
//                val urlText = binding.urlVideo.text.toString()
//                intent.putExtra(Intent.EXTRA_TEXT, content)
//                intent.putExtra(Intent.EXTRA_HTML_TEXT, urlText)
//                setResult(Activity.RESULT_OK, intent)
//
//                finish()
//            }
        }

        return binding.root

    }
}

