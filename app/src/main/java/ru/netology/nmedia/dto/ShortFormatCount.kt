package ru.netology.nmedia.dto

class ShortFormatCount() {

    fun countFormat(count: Long): String {
        val countShortK: Long = count / 1000
        val countShortK2: Long = (count % 1000) / 100
        val countShortM: Long = count / 1000000
        val countShortM2: Long = (count % 1000000) / 100000
        return when (count) {
            in 1000..1099, in 10000..999999 -> "$countShortK" + "K"
            in 1100..9999 -> "$countShortK" + ".$countShortK2" + "K"
            in 1000000..1099999 -> "$countShortM" + "M"
            in 1100000..Long.MAX_VALUE -> "$countShortM" + ".$countShortM2" + "M"
            else -> "$count"
        }
    }
}