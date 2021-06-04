package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.myapplication.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.awaitResponse

class SecondActivity : AppCompatActivity() {

    private lateinit var avatar: ImageView
    private lateinit var fname: TextView
    private lateinit var lname: TextView
    private lateinit var email: TextView
    private lateinit var id: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        avatar = findViewById(R.id.avatar)
        fname = findViewById(R.id.firstName2)
        lname = findViewById(R.id.lastName2)
        email = findViewById(R.id.email)
        id = findViewById(R.id.id)

        val userId = intent.extras?.getLong("ID")

        getData(userId)

    }

    fun getData(userId: Long?){

        GlobalScope.launch(Dispatchers.IO) {

            try {
                val response = RetrofitClient.reqResApi.getUser(userId!!).awaitResponse()

                if (response.isSuccessful) {
                    val user = response.body()?.data!!

                    withContext(Dispatchers.Main) {
                        fname.text = user.firstName
                        lname.text = user.lastName
                        email.text = user.email
                        id.text = user.id.toString()

                        Glide.with(this@SecondActivity)
                            .load(user.avatar)
                            .fitCenter()
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .into(avatar)
                    }

                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

}