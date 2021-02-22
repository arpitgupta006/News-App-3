package com.arpit.newsapp3

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class MyAdapter( val context: Context , val articles: List<Article> ) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    class MyViewHolder(itemView : View) :RecyclerView.ViewHolder(itemView){
        var tvTitle = itemView.findViewById<TextView>(R.id.tvTitle)
        var tvDescription = itemView.findViewById<TextView>(R.id.tvDescription)
        var ivNewsImage = itemView.findViewById<ImageView>(R.id.ivNewsImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view, parent , false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.tvTitle.text = articles[position].title
        holder.tvDescription.text = articles[position].description
        Glide.with(context).load(articles[position].urlToImage).into(holder.ivNewsImage)
        holder.itemView.setOnClickListener {
            Toast.makeText(context , articles[position].title , Toast.LENGTH_SHORT).show()
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("URL" , articles[position].url)
            context.startActivity(intent)
        }
//        var color ="#000000"
//        if(position % 2== 0){
//            color = "#FF5A4D4D"
//        }
//        holder.container.setBackgroundColor(Color.parseColor(color))

    }

    override fun getItemCount() = articles.size
}

