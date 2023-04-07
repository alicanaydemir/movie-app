package com.aydemir.movieapp.ui.movieDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.aydemir.core.base.BaseAdapter
import com.aydemir.core.base.BaseViewHolder
import com.aydemir.movieapp.data.model.Genre
import com.aydemir.movieapp.databinding.ItemGenreBinding

class MovieGenreAdapter : BaseAdapter<Genre>(object : DiffUtil.ItemCallback<Genre>() {
    override fun areItemsTheSame(oldItem: Genre, newItem: Genre): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Genre, newItem: Genre): Boolean {
        return false
    }

}) {

    override fun createViewHolderInstance(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewBinding> {
        val binding: ItemGenreBinding =
            ItemGenreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = BaseViewHolder(binding)

        return holder
    }

    override fun bind(binding: ViewBinding, position: Int) {
        (binding as ItemGenreBinding).apply {
            this.txtGenreName.text = getItem(
                position
            ).name
        }
    }
}