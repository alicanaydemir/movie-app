package com.aydemir.movieapp.ui.movieDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import coil.load
import com.aydemir.core.base.BaseAdapter
import com.aydemir.core.base.BaseViewHolder
import com.aydemir.core.base.Constants
import com.aydemir.movieapp.data.model.Cast
import com.aydemir.movieapp.databinding.ItemCastBinding

class MovieCastAdapter : BaseAdapter<Cast>(object : DiffUtil.ItemCallback<Cast>() {
    override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return false
    }

}) {

    override fun createViewHolderInstance(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<ViewBinding> {
        val binding: ItemCastBinding =
            ItemCastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val holder = BaseViewHolder(binding)

        return holder
    }

    override fun bind(binding: ViewBinding, position: Int) {
        (binding as ItemCastBinding).apply {
            this.imgProfile.load(
                Constants.Pref.BASE_IMG_URL + getItem(
                    position
                ).profilePath
            )
            this.txtCastName.text = getItem(
                position
            ).name
        }
    }
}