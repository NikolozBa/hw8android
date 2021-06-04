package com.example.myapplication

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.api.dto.User


class RecyclerAdapter (private val list: List<User>): RecyclerView.Adapter<RecyclerAdapter.RecyclerViewHolder>() {


    class RecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val userFName: TextView = itemView.findViewById(R.id.firstName)
        val userLName: TextView = itemView.findViewById(R.id.lastName)
        val img: ImageView = itemView.findViewById(R.id.img)
        val clickArea: ConstraintLayout = itemView.findViewById(R.id.clickArea)

        fun bindUser(user: User) {

            Glide.with(itemView)
                .load(user.avatar)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(img)

            userFName.text = user.firstName
            userLName.text = user.lastName

            clickArea.setOnClickListener {
                val intent = Intent(itemView.context, SecondActivity::class.java)
                intent.putExtra("ID", user.id)
                itemView.context.startActivity(intent)

            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        holder.bindUser(list[position])
    }

}