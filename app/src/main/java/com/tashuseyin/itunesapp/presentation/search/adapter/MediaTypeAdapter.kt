package com.tashuseyin.itunesapp.presentation.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tashuseyin.itunesapp.R
import com.tashuseyin.itunesapp.databinding.ItemFilterBinding

class MediaTypeAdapter : RecyclerView.Adapter<MediaTypeAdapter.MediaTypeViewHolder>() {
    private var mediaTypeList: List<String> = emptyList()
    var onItemClickListener: ((String) -> Unit)? = null
    private var defaultMediaTypeId = 0

    inner class MediaTypeViewHolder(private val binding: ItemFilterBinding) :
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
    ): MediaTypeViewHolder {
        val binding =
            ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MediaTypeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MediaTypeViewHolder, position: Int) {
        holder.bind(mediaTypeList[position], onItemClickListener)
    }

    override fun getItemCount() = mediaTypeList.size


    fun setData(newMediaTypeList: List<String>) {
        this.mediaTypeList = newMediaTypeList
    }
}