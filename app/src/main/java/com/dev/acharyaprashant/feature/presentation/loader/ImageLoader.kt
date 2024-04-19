package com.dev.acharyaprashant.feature.presentation.loader

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.LruCache
import android.widget.ImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

object ImageLoader {

    private const val cacheSize = 100 * 1024 * 1024 // 100 MB
    private val imageCache = object : LruCache<String, Bitmap>(cacheSize) {
        override fun sizeOf(key: String, value: Bitmap): Int {
            return value.byteCount
        }
    }

    fun loadImage(url: String, imageView: ImageView, placeholderResId: Int) {
        val bitmap = imageCache.get(url)
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap)
        } else {
            imageView.setImageResource(placeholderResId)
            CoroutineScope(Dispatchers.IO).launch {
                val loadedBitmap = downloadImage(url)
                loadedBitmap?.let {
                    imageCache.put(url, it)
                    CoroutineScope(Dispatchers.Main).launch {
                        imageView.setImageBitmap(it)
                    }
                } ?: imageView.setImageResource(placeholderResId)
            }
        }
    }


    private fun downloadImage(url: String): Bitmap? {
        val connection: HttpURLConnection = URL(url).openConnection() as HttpURLConnection
        connection.doInput = true
        connection.connect()
        val input: InputStream = connection.inputStream
        return BitmapFactory.decodeStream(input)
    }
}