package com.dev.acharyaprashant.feature.presentation.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.dev.acharyaprashant.databinding.ItemLoadStateBinding

class LoaderAdapter(private val retry: () -> Unit): LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        return LoaderViewHolder(ItemLoadStateBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState)

        holder.binding.retry.setOnClickListener {
            retry()
        }


    }

  class LoaderViewHolder(val binding:ItemLoadStateBinding):ViewHolder(binding.root){
      fun bind(loadState: LoadState) {
          if (loadState is LoadState.Error) {
              binding.errorMsg.text = loadState.error.localizedMessage
          }
          binding.progressBar.isVisible = loadState is LoadState.Loading
          binding.retry.isVisible = loadState is LoadState.Error
          binding.errorMsg.isVisible = loadState is LoadState.Error
      }
  }


}