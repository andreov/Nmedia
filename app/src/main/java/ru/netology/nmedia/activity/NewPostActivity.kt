package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity() {

    companion object {
        const val EDIT_CONTENT_KEY = "key1"
        const val IS_UPDATE_POST_KEY = "key2"
        const val EDIT_URL_KEY = "key3"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val isUpdatePost= intent?.getBooleanExtra(IS_UPDATE_POST_KEY, false) == true
        if(isUpdatePost){
            val editContent = intent?.getStringExtra(EDIT_CONTENT_KEY)
            binding.edit.setText(editContent)
            val editUrl = intent?.getStringExtra(EDIT_URL_KEY)
            binding.urlVideo.setText(editUrl)
        }
        else {
            binding.edit.setText("")
            binding.edit.requestFocus()
        }

        binding.ok.setOnClickListener {
            val intent = Intent()
            if (binding.edit.text.isNullOrBlank()) {
                Snackbar.make(binding.root, R.string.error_empty_content, LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok) {
                        setResult(Activity.RESULT_CANCELED, intent)
                        finish()
                    }
                    .show()

            } else {
                val content = binding.edit.text.toString()
                val urlText = binding.urlVideo.text.toString()
                intent.putExtra(Intent.EXTRA_TEXT, content)
                intent.putExtra(Intent.EXTRA_HTML_TEXT, urlText)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
        }
    }
}

