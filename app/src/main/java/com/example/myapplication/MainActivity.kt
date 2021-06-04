package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.myapplication.api.RetrofitClient
import com.example.myapplication.api.dto.ReqResData
import com.example.myapplication.api.dto.User
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse

class MainActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private lateinit var adapter: RecyclerAdapter
    private lateinit var refresh: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler = findViewById(R.id.recycler)
        recycler.layoutManager = LinearLayoutManager(this)
        refresh = findViewById(R.id.refreshUsers)
        refresh.isRefreshing = true

        getUsers()

        refresh.setOnRefreshListener {
            getUsers()
        }

    }

    private fun getUsers() {
        GlobalScope.launch(Dispatchers.IO) {

            try {
                val response = RetrofitClient.reqResApi.getUsers(2).awaitResponse()

                if (response.isSuccessful) {
                    val list = response.body()?.data!!

                    withContext(Dispatchers.Main) {
                        adapter = RecyclerAdapter(list)
                        adapter.notifyDataSetChanged()
                        recycler.adapter = adapter
                        refresh.isRefreshing = false
                    }

                }
            }catch (e: Exception){
                withContext(Dispatchers.Main){
                    Toast.makeText(applicationContext, "Something went wrong", Toast.LENGTH_SHORT).show()
                    refresh.isRefreshing = false
                }
            }
        }

//        RetrofitClient.reqResApi.getUsers(2).enqueue(object : Callback<ReqResData<List<User>>> {
//
//            override fun onResponse(
//                call: Call<ReqResData<List<User>>>,
//                response: Response<ReqResData<List<User>>>
//            ) {
//                if (response.isSuccessful && response.body() != null) {
//                    response.body()?.data?.forEach { user -> Log.d("MyData", user.toString()) }
//                }
//            }
//
//            override fun onFailure(call: Call<ReqResData<List<User>>>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })

    }

}