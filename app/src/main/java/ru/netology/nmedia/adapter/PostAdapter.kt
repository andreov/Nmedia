package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.CardPostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.dto.ShortFormatCount


interface OnInteractionListener {
    fun onLike(post: Post) {}
    fun onEdit(post: Post) {}
    fun onRemove(post: Post) {}
    fun onShare(post: Post) {}
    fun onVideo(post: Post) {}
}

class PostAdapter(private val onInteractionListener: OnInteractionListener
) : ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val binding = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val post = getItem(position)
        holder.bind(post)
    }
}

class PostViewHolder(
    private val binding: CardPostBinding,
    private val onInteractionListener: OnInteractionListener,
    private val shortFormatCount:ShortFormatCount = ShortFormatCount()
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(post: Post) {
        binding.apply {
            author.text = post.author
            published.text = post.published
            content.text = post.content
            urlText.text = post.urlVideo
            imageLike.text = shortFormatCount.countFormat(post.countLike)
            imageShare.text = shortFormatCount.countFormat(post.countShare)

            if(urlText.text == "") video.visibility = View.GONE
            else {
                video.visibility = View.VISIBLE
                video.setImageResource(R.mipmap.ic_banner_foreground)
            }

            imageLike.isChecked = post.likedByMe

            imageLike.setOnClickListener{
                onInteractionListener.onLike(post)
            }
            imageShare.setOnClickListener {
                onInteractionListener.onShare(post)
            }
            video.setOnClickListener {
                onInteractionListener.onVideo(post)
            }
            menu.setOnClickListener{
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)           // пункты меню
                    setOnMenuItemClickListener { item ->  // обработчик клика пункта меню
                        when(item.itemId) {
                            R.id.remove -> {
                                onInteractionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                onInteractionListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()  //показ меню
            }
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }
}