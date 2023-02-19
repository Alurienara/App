package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var imageUrl: String
    //   execute on the main thread, empty constructor is deprecated
    //    инициировали объект класса Handler
    private lateinit var handler: Handler
    //    инициировали блок выполняемого кода
    private lateinit var runnableCode: Runnable

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //hide status bar
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

//      пересоздали явно объект класса Handler и запускаем его в основном потоке
        handler = Handler(Looper.getMainLooper())

        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        //    счетчик для перебора списка
        var index = 0

//      пересоздали явно объект класса Runnable
        runnableCode = Runnable {
//          путь к картинкам (массиву). Можно заменить на единственную ссылку
//            imageUrl = savedInstanceState?.getString(KEY_IMAGE_URL) ?: imagesList[Random.nextInt(
//                imagesList.size
//            )]
            imageUrl = savedInstanceState?.getString(KEY_IMAGE_URL) ?: imagesList[index]
            index+=1
            if (index == imagesList.size) {
                index = 0
            }
//          Работаем с glide для вывода на экран картинки (см выше)
            Glide.with(this)
                .load(imageUrl)
                .into(binding.imageView)
//          обращаемся к объекту класса handler и с задержкой в [2] (второй аргумент в мс)
//          выполняем функцию runnableCode (рекурсия?). 900 000 мс = 15 минут.
            handler.postDelayed(runnableCode, 2000)
        }
//      запуск вышенаписанного кода
        handler.post(runnableCode)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_IMAGE_URL, imageUrl)
    }

    companion object {
        const val KEY_IMAGE_URL = "KEY_IMAGE_URL"

//        url картинок
        val imagesList: List<String> = listOf(
            "http://192.168.1.100/wall.jpg"
        )
//        заменяем все url выше на локальную ссылку или правим путь
//        "http://192.168.1.100/wall.jpg"
//        последняя сссылка - на локальный сервер с картинкой, остальные - пример работы (кажется не все отображаются, возможно косячные ссылки)
    }
}
