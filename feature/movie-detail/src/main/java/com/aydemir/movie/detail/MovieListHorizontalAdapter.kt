package com.aydemir.movie.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import coil.load
import com.aydemir.core.base.BaseAdapter
import com.aydemir.core.base.BaseEvent
import com.aydemir.core.base.BaseViewHolder
import com.aydemir.core.base.Constants
import com.aydemir.core.base.EventListener
import com.aydemir.core.database.model.Movie
import com.aydemir.movie.detail.databinding.ItemMovieHorizontalBinding

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
            this.txtMovieTitle.text = getItem(
                position
            ).title
        }
    }
}

sealed class MovieListHorizontalAdapterEvent : BaseEvent {
    class ClickMovie(val data: Movie) : MovieListHorizontalAdapterEvent()
}