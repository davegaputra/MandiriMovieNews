package com.example.mandirimovienews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mandirimovienews.databinding.ItemMotionMovieBinding
import com.example.mandirimovienews.model.Movie
import javax.inject.Inject

class MovieMotionAdapter @Inject constructor() : RecyclerView.Adapter<MovieMotionAdapter.MovieMotionViewHolder>(){

    var movieMotionClickListener: MovieMotionClickListener? = null

    inner class MovieMotionViewHolder(val item : ItemMotionMovieBinding): RecyclerView.ViewHolder(item.root)

    private val differCallback = object : DiffUtil.ItemCallback<Movie>(){
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.posterPath == newItem.posterPath
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,  differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieMotionViewHolder {
        return  MovieMotionViewHolder(
            ItemMotionMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: MovieMotionViewHolder, position: Int) {
        val movie = differ.currentList[position]
        holder.item.movie = movie
        holder.itemView.setOnClickListener {
            movieMotionClickListener?.onMovieMotionClicked(holder.item, movie)
        }
    }

    interface MovieMotionClickListener {
        fun onMovieMotionClicked(binding: ItemMotionMovieBinding, movie: Movie)
    }
}