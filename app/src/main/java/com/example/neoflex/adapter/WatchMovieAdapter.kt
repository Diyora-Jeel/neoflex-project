package com.example.neoflex.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.neoflex.R
import com.example.neoflex.model.MovieWatchModel
import com.example.neoflex.onClick.MovieOnClick

class WatchMovieAdapter(var list : ArrayList<MovieWatchModel>, private val context: Context, private val movieOnClick: MovieOnClick) : RecyclerView.Adapter<WatchMovieAdapter.HindiViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HindiViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.movie_watch_item, parent, false)
        return HindiViewHolder(view)
    }

    fun filterList(filterlist: ArrayList<MovieWatchModel>) {
        list = filterlist
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: HindiViewHolder, position: Int) {

        val item = list[position]

        holder.title.text = item.title

        Glide.with(context).load(item.poster).placeholder(R.drawable.ic_no_image).into(holder.poster)

        holder.cardView.setOnClickListener {
            movieOnClick.onMovieData(item.id.toString(),item.title.toString(),item.poster.toString(),item.link.toString())
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    class HindiViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val poster : ImageView = itemView.findViewById(R.id.posterIv)
        val title : TextView = itemView.findViewById(R.id.titleTv)
        val cardView : CardView = itemView.findViewById(R.id.cardView)
    }
}