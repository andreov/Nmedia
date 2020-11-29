package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*  // установка в Gradle
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var likedByMe = false
    private var countLike: Long = 999
    private var countShare: Long = 1099
    private val shortFormatCount = ShortFormatCount()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val binding = ActivityMainBinding.inflate(layoutInflater)
        //setContentView(binding.root)
        setContentView(R.layout.activity_main)

        textLike.text = shortFormatCount.countFormat(countLike)
        textShare.text = shortFormatCount.countFormat(countShare)

        imageLike.setOnClickListener {
            if (!likedByMe) {
                imageLike.setImageResource(R.drawable.ic_baseline_favorite_24)
                countLike++
                textLike.text = shortFormatCount.countFormat(countLike)
                likedByMe = true
            } else {
                imageLike.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                countLike--
                textLike.text = shortFormatCount.countFormat(countLike)
                likedByMe = false
            }
        }

        imageShare.setOnClickListener {
            countShare++
            textShare.text = shortFormatCount.countFormat(countShare)
        }

    }
}