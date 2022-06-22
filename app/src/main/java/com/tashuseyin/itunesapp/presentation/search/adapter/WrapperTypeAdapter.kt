package com.tashuseyin.itunesapp.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tashuseyin.itunesapp.R
import com.tashuseyin.itunesapp.databinding.ItemFilterBinding

class WrapperTypeAdapter : RecyclerView.Adapter<WrapperTypeAdapter.WrapperTypeViewHolder>() {
    private var mediaTypeList: List<String> = emptyList()
    var onItemClickListener: ((String) -> Unit)? = null
    private var defaultMediaTypeId = 0

    inner class WrapperTypeViewHolder(private val binding: ItemFilterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(mediaType: String, onItemClickListener: ((String) -> Unit)?) {
            binding.apply {
                chip.text = mediaType

                if (defaultMediaTypeId == absoluteAdapterPosition) {
                    cardView.setCardBackgroundColor(
                        cardView.context.getColor(
                            R.color.primary
                        )
                    )
                    chip.setTextColor(
                        cardView.context.getColor(
                            R.color.white
                        )
                    )

                } else {
                    cardView.setCardBackgroundColor(
                        cardView.context.getColor(
                            R.color.light_gray
                        )
                    )
                    chip.setTextColor(
                        cardView.context.getColor(
                            R.color.medium_gray
                        )
                    )
                }

                cardView.setOnClickListener {
                    defaultMediaTypeId = absoluteAdapterPosition
                    onItemClickListener?.invoke(mediaType)
                    notifyDataSetChanged()
                }
            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WrapperTypeViewHolder {
        val binding =
            ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WrapperTypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WrapperTypeViewHolder, position: Int) {
        holder.bind(mediaTypeList[position], onItemClickListener)
    }

    override fun getItemCount() = mediaTypeList.size


    fun setData(newMediaTypeList: List<String>) {
        this.mediaTypeList = newMediaTypeList
    }
}