package ru.netology.nmedia.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class NewPost : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //binding.edit.requestFocus()

        val bundle = intent.extras
        var editContent:String? = null
        var isUpdatePost:Boolean=false
        isUpdatePost= bundle!!.getBoolean("key2")
        if(isUpdatePost){
            editContent = bundle!!.getString("key1", "Default")
            binding.edit.setText(editContent)
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
                intent.putExtra(Intent.EXTRA_TEXT, content)
                setResult(Activity.RESULT_OK, intent)
                finish()
            }
            //finish()
        }
    }
}

