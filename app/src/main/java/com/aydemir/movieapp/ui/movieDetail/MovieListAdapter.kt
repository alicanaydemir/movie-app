package com.aydemir.movieapp.ui.movieDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import coil.load
import com.aydemir.core.base.*
import com.aydemir.movieapp.data.model.Movie
import com.aydemir.movieapp.databinding.ItemMovieBinding

class MovieListAdapter(private val listener: EventListener) :
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
        val binding: ItemMovieBinding = ItemMovieBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        val holder = BaseViewHolder(binding)
        holder.binding.root.setOnClickListener {
            listener.invoke(MovieListAdapterEvent.ClickMovie(getItem(holder.bindingAdapterPosition)))
        }
        return holder
    }

    override fun bind(binding: ViewBinding, position: Int) {
        (binding as ItemMovieBinding).apply {
            this.imgMoviePosterPath.load(
                Constants.Pref.BASE_IMG_URL + getItem(
                    position
                ).posterPath
            )
            this.txtMovieTitle.text = getItem(
                position
            ).title
        }
    }
}

sealed class MovieListAdapterEvent : BaseEvent {
    class ClickMovie(val data: Movie) : MovieListAdapterEvent()
}