package com.dev.acharyaprashant.feature.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.dev.acharyaprashant.core.exception.ApiException
import com.dev.acharyaprashant.databinding.ActivityHomeBinding
import com.dev.acharyaprashant.feature.presentation.arch.MediaViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private val viewModel : MediaViewModel by viewModels()
    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = MediaPagingAdapter()
        binding.rvHome.layoutManager = GridLayoutManager(this, 3)
        binding.rvHome.setHasFixedSize(true)
        binding.rvHome.adapter = adapter.withLoadStateHeaderAndFooter(
            header = LoaderAdapter(adapter::retry),
            footer = LoaderAdapter(adapter::retry)
        )
        adapter.addLoadStateListener { loadState ->

            binding.apply {
                when {
                    loadState.refresh is LoadState.Error ->
                        showError(loadState.refresh as? LoadState.Error)

                    loadState.append is LoadState.Error ->
                        showError(loadState.append as? LoadState.Error)

                    loadState.prepend is LoadState.Error ->
                        showError(loadState.prepend as? LoadState.Error)
                }
            }
        }
        viewModel.mediaLiveData.observe(this, Observer {
            adapter.submitData(lifecycle, it)
        })

    }

    private fun showError(errorState: LoadState.Error?) {
        errorState?.let {
            Toast.makeText(this, "${it.error.message}", Toast.LENGTH_SHORT)
                .show()
        }
    }
}