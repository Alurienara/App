package com.example.myapplication

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ActivityMainBinding
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var imageUrl: String

    override fun onCreate(savedInstanceState: Bundle?) {

        //hide status bar
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN )


        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also {setContentView(it.root)}

        imageUrl = savedInstanceState?.getString(KEY_IMAGE_URL) ?: imagesList[Random.nextInt(imagesList.size)]

        Glide.with(this)
            .load(imageUrl)
            .into(binding.imageView)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_IMAGE_URL, imageUrl)
    }

    companion object {
        const val KEY_IMAGE_URL = "KEY_IMAGE_URL"

        val imagesList:List<String> = listOf(
            "https://fotorelax.ru/wp-content/uploads/2016/12/Beautiful-nature-of-Canadas-landscapes-Mark-Jinks-23.jpg",
            "https://crosti.ru/patterns/00/0d/c6/0d_picture_20df317f.jpg",
            "https://img-fotki.yandex.ru/get/97201/127908635.1418/0_1ac2f7_fa876a0_orig.jpg"
        )
    }
}