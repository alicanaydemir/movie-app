package com.aydemir.movieapp.ui.movieDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.aydemir.movieapp.core.BaseAdapter
import com.aydemir.movieapp.core.BaseViewHolder
import com.aydemir.movieapp.data.model.Backdrop
import com.aydemir.movieapp.databinding.ItemBackdropsBinding

class BackDropsAdapter : BaseAdapter<Backdrop>(object : DiffUtil.ItemCallback<Backdrop>() {
    override fun areItemsTheSame(oldItem: Backdrop, newItem: Backdrop): Boolean {
        return oldItem.filePath == newItem.filePath
    }

    override fun areContentsTheSame(oldItem: Backdrop, newItem: Backdrop): Boolean {
        return false
    }

}) {
    override fun createViewHolderInstance(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewBinding> {
        val binding: ItemBackdropsBinding =
            ItemBackdropsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = BaseViewHolder(binding)


        return holder
    }

    override fun bind(binding: ViewBinding, position: Int) {

    }
}