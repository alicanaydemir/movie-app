package com.aydemir.core.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

open class BaseViewHolder<out T : ViewBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)