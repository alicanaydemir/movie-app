package com.aydemir.movieapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.aydemir.movieapp.core.BaseViewHolder
import com.aydemir.movieapp.databinding.ItemHeaderTitleBinding

class HeaderAdapter(private val title: String) :
    RecyclerView.Adapter<BaseViewHolder<ViewBinding>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding> {
        val binding: ItemHeaderTitleBinding =
            ItemHeaderTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = BaseViewHolder(binding)
        return holder
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding>, position: Int) {
        (holder.binding as ItemHeaderTitleBinding).txtTitle.text = title
    }
}