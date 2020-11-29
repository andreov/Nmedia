package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*  // установка в Gradle

class MainActivity : AppCompatActivity() {

    private var likedByMe = false
    private var countLike: Long = 999
    private var countShare: Long = 1099
    private val shortFormatCount = ShortFormatCount()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        clickLike()
        clickShare()

    }

    private fun initViews(){
        textLike?.text = shortFormatCount.countFormat(countLike)
        textShare?.text = shortFormatCount.countFormat(countShare)
    }

    private fun clickLike() {
        imageLike?.setOnClickListener {
            if (!likedByMe) {
                imageLike?.setImageResource(R.drawable.ic_baseline_favorite_24)
                countLike++
                textLike?.text = shortFormatCount.countFormat(countLike)
                likedByMe = true
            } else {
                imageLike?.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                countLike--
                textLike?.text = shortFormatCount.countFormat(countLike)
                likedByMe = false
            }
        }
    }

    private fun clickShare() {
        imageShare?.setOnClickListener {
            countShare++
            textShare?.text = shortFormatCount.countFormat(countShare)
        }
    }


}