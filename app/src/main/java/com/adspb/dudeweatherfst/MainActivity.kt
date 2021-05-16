package com.adspb.dudeweatherfst

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {
    //Создаем и инициализируем  объекты налл

    private var user_field:EditText? = null
    private var main_btn:Button? = null
    private var result_info:TextView? = null

    @SuppressLint("SetTextI18n")//лучше не использовать
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Присваиваем полям ссылки на объекты из Активити

        user_field = findViewById(R.id.user_field)
        main_btn = findViewById(R.id.main_btn)
        result_info = findViewById(R.id.result_info)

        //Обработчик события.Знак вопроса так как ранее был определен
        main_btn?.setOnClickListener {
            if (user_field?.text?.toString()?.trim()?.equals("")!!)
                Toast.makeText(this, "Введите город", Toast.LENGTH_LONG).show()
            //Показать пуш 3 сек
            else {
                val city: String = user_field?.text.toString() //create var and declarate text
                val key: String = "a011aeb88485bdc0e73f33da6007a535"
                val url: String = "https://api.openweathermap.org/data/2.5/weather?q=$city&appid=$key"

                doAsync {
                    val apiResponse = URL(url).readText() //считать весь текст полученный по ссылке
                    Log.d("INFO", apiResponse) //для тестирования в консоли можно посмотреть что присвоилось

                    val weather = JSONObject(apiResponse).getJSONArray("weather")
                    val description = weather.getJSONObject(0).getString("description")

                    val main = JSONObject(apiResponse).getJSONArray("main")
                    val temp = main.getJSONObject(0).getString("temp")

                    result_info?.text = "Температура: $temp\n$description" //вызывает аннотацию @SuppressLint - лучше не использовать

                }


            }
        }
    }
}