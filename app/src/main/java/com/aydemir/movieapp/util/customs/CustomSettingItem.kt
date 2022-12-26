package com.aydemir.movieapp.util.customs

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.aydemir.movieapp.R
import com.aydemir.movieapp.databinding.ViewCustomSettingItemBinding

class CustomSettingItem(context: Context?, private val attrs: AttributeSet?) :
    RelativeLayout(context, attrs) {

    private var binding: ViewCustomSettingItemBinding

    init {
        binding = ViewCustomSettingItemBinding.inflate(LayoutInflater.from(context), this)
        setupAttributes()
    }

    private fun setupAttributes() {
        // Obtain a typed array of attributes
        val typedArray =
            context.theme.obtainStyledAttributes(attrs, R.styleable.CustomSettingItem, 0, 0)
        // Extract custom attributes into member variables
        try {
            binding.txtTitle.text = typedArray.getString(R.styleable.CustomSettingItem_title)
            val icon = typedArray.getDrawable(R.styleable.CustomSettingItem_icon)
            binding.txtTitle.setCompoundDrawablesWithIntrinsicBounds(
                null, null, icon, null
            )
        } finally {
            // TypedArray objects are shared and must be recycled.
            typedArray.recycle()
        }
    }

    fun setTitle(title: String) {
        binding.txtTitle.text = title
    }

    fun setDrawable(drawable: Drawable?) {
        binding.txtTitle.setCompoundDrawablesWithIntrinsicBounds(
            null, null, drawable, null
        )
    }


}