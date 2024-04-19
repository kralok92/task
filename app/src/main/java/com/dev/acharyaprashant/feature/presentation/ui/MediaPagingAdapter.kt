package com.dev.acharyaprashant.feature.presentation.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.dev.acharyaprashant.R
import com.dev.acharyaprashant.databinding.MediaItemBinding
import com.dev.acharyaprashant.feature.data.models.MediaResult
import com.dev.acharyaprashant.feature.presentation.loader.ImageLoader
import com.squareup.picasso.Picasso

class MediaPagingAdapter : PagingDataAdapter<MediaResult, MediaPagingAdapter.MediaViewHolder>(
    COMPARATOR
) {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        return MediaViewHolder(MediaItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val data = getItem(position)
        data?.let {
            ImageLoader.loadImage("https://image.tmdb.org/t/p/w500${it.poster_path}",holder.binding.itemImg,
                R.drawable.demo)
        }
    }



    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<MediaResult>() {
            override fun areItemsTheSame(oldItem: MediaResult, newItem: MediaResult): Boolean {
                return oldItem.id == newItem.id

            }

            override fun areContentsTheSame(oldItem: MediaResult, newItem: MediaResult): Boolean {
                return oldItem == newItem
            }
        }
    }

    class MediaViewHolder(val binding: MediaItemBinding) : RecyclerView.ViewHolder(binding.root)

}





















