package com.aydemir.movieapp.ui.movieDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import coil.load
import com.aydemir.movieapp.core.*
import com.aydemir.movieapp.data.model.Movie
import com.aydemir.movieapp.databinding.ItemMovieHorizontalBinding

class MovieListHorizontalAdapter(private val listener: EventListener) :
    BaseAdapter<Movie>(object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return false
        }

    }) {
    override fun createViewHolderInstance(
        parent: ViewGroup, viewType: Int
    ): BaseViewHolder<ViewBinding> {
        val binding: ItemMovieHorizontalBinding = ItemMovieHorizontalBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val holder = BaseViewHolder(binding)
        holder.binding.root.setOnClickListener {
            listener.invoke(MovieListHorizontalAdapterEvent.ClickMovie(getItem(holder.bindingAdapterPosition)))
        }
        return holder
    }

    override fun bind(binding: ViewBinding, position: Int) {
        (binding as ItemMovieHorizontalBinding).apply {
            this.imgMoviePosterPath.load(
                Constants.Pref.BASE_IMG_URL + getItem(
                    position
                ).posterPath
            )
        }
    }
}

sealed class MovieListHorizontalAdapterEvent : BaseEvent {
    class ClickMovie(val data: Movie) : MovieListHorizontalAdapterEvent()
}