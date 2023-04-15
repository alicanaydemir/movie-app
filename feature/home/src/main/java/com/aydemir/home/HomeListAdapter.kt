package com.aydemir.home

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.aydemir.core.base.BaseAdapter
import com.aydemir.core.base.BaseEvent
import com.aydemir.core.base.BaseViewHolder
import com.aydemir.core.base.EventListener
import com.aydemir.core.database.model.Movie
import com.aydemir.home.databinding.ItemHomeHeaderBinding
import com.aydemir.home.databinding.ItemHomeListBinding

class HomeListAdapter(private val listener: EventListener) :
    BaseAdapter<HomeItem>(object : DiffUtil.ItemCallback<HomeItem>() {
        override fun areItemsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: HomeItem, newItem: HomeItem): Boolean {
            return false
        }

    }) {

    private val mBoundViewHolders = hashMapOf<Int, BaseViewHolder<ViewBinding>>()

    private val scrollStates = mutableMapOf<Int, Parcelable?>()

    override fun createViewHolderInstance(
        parent: ViewGroup, viewType: Int
    ): BaseViewHolder<ViewBinding> {
        if (viewType == 0) {
            val binding: ItemHomeHeaderBinding = ItemHomeHeaderBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            val holder = BaseViewHolder(binding)
            holder.binding.root.setOnClickListener {
                listener.invoke(HomeListAdapterEvent.ClickHeader(getItem(holder.bindingAdapterPosition)))
            }
            return holder
        } else {
            val binding: ItemHomeListBinding = ItemHomeListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            val holder = BaseViewHolder(binding)

            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(
                    this.context, LinearLayoutManager.HORIZONTAL, false
                )
                val movieListHorizontalAdapter = MovieListHorizontalAdapter {
                    if (it is MovieListHorizontalAdapterEvent.ClickMovie) {
                        listener.invoke(HomeListAdapterEvent.ClickMovie(it.data))
                    }
                }
                adapter = movieListHorizontalAdapter
            }
            return holder
        }
    }

    override fun bind(binding: ViewBinding, position: Int) {
        if (binding is ItemHomeHeaderBinding) {
            binding.apply {
                txtTitle.text = getItem(position).txtHeader
            }
        } else if (binding is ItemHomeListBinding) {

            binding.recyclerView.apply {
                (adapter as MovieListHorizontalAdapter).submitList(getItem(position).data)
                isNestedScrollingEnabled = false
                val state = scrollStates[position]
                if (state != null) {
                    layoutManager?.onRestoreInstanceState(state)
                } else {
                    layoutManager?.scrollToPosition(0)
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: BaseViewHolder<ViewBinding>) {
        super.onViewAttachedToWindow(holder)
        when (holder.binding) {
            is ItemHomeListBinding -> {
                mBoundViewHolders[holder.bindingAdapterPosition] = holder
            }
        }
    }

    override fun onViewRecycled(holder: BaseViewHolder<ViewBinding>) {
        super.onViewRecycled(holder)
        when (holder.binding) {
            is ItemHomeListBinding -> {
                val key = holder.bindingAdapterPosition
                scrollStates[key] =  (holder.binding as ItemHomeListBinding).recyclerView.layoutManager?.onSaveInstanceState()
            }
        }
    }

    fun saveStateRecyclerViews() {
        scrollStates.clear()
        mBoundViewHolders.forEach {
            it.value
            if (it.value.binding is ItemHomeListBinding) {
                scrollStates[it.key] =
                    (it.value.binding as ItemHomeListBinding).recyclerView.layoutManager?.onSaveInstanceState()

            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).isHeader) 0
        else 1
    }
}

sealed class HomeListAdapterEvent : BaseEvent {
    class ClickHeader(val data: HomeItem) : HomeListAdapterEvent()
    class ClickMovie(val data: Movie) : HomeListAdapterEvent()
}