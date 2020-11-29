package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
//import kotlinx.android.synthetic.main.activity_main.*  // установка в Gradle
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var likedByMe = false
    private var countLike: Long = 999
    private var countShare: Long = 1099
    private val shortFormatCount = ShortFormatCount()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main)

        binding.textLike.text = shortFormatCount.countFormat(countLike)
        binding.textShare.text = shortFormatCount.countFormat(countShare)

        binding.imageLike.setOnClickListener {
            Log.d("stuff", "like")
            if (!likedByMe) {
                binding.imageLike.setImageResource(R.drawable.ic_baseline_favorite_24)
                countLike++
                binding.textLike.text = shortFormatCount.countFormat(countLike)
                likedByMe = true
            } else {
                binding.imageLike.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                countLike--
                binding.textLike.text = shortFormatCount.countFormat(countLike)
                likedByMe = false
            }
        }

        binding.imageShare.setOnClickListener {
            Log.d("stuff", "share")
            countShare++
            binding.textShare.text = shortFormatCount.countFormat(countShare)
        }

    }
}