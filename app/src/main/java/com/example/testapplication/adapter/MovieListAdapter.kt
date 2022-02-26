package com.example.testapplication.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapplication.R
import com.example.testapplication.databinding.ItemMovieListBinding
import com.example.testapplication.di.IMAGE_BASE_URL
import com.example.testapplication.network.Movie

class MovieListAdapter(
    private val context: Context,
    movieList: List<Movie>,
    ) : RecyclerView.Adapter<MovieListAdapter.MovieListViewHolder>() {
    private val movieMutableList = movieList as MutableList<Movie>
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieListViewHolder {
        val binding = ItemMovieListBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieListViewHolder(binding)
    }

    override fun getItemCount() = movieMutableList.size

    override fun onBindViewHolder(holder: MovieListViewHolder, position: Int) {
       with(holder){
           with(movieMutableList[position]) {
               Glide.with(holder.itemView.context)
                   .load(IMAGE_BASE_URL+this.poster_path)
                   .centerCrop()
                   .into(binding.imageViewMoviePoster)

               holder.binding.textViewMovieTitle.text = this.title
               holder.binding.textViewMovieRating.text = "${context.getString(R.string.text_rating)}: ${this.vote_average}"
           }
       }
    }

    inner class MovieListViewHolder(val binding: ItemMovieListBinding) : RecyclerView.ViewHolder(binding.root)
}