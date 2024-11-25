package com.example.restaurantreview.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurantreview.data.remote.response.ListEventsItem
import com.example.restaurantreview.databinding.ItemEventBinding
import com.example.restaurantreview.ui.detail.DetailActivity

class EventAdapter : ListAdapter<ListEventsItem, EventAdapter.EventViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EventViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = getItem(position)
        holder.bind(event)

        holder.itemView.setOnClickListener {
            // Send event ID ke DetailActivity pakai Intent
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_EVENT_ID, event.id.toString())
            holder.itemView.context.startActivity(intent)
        }
    }

    inner class EventViewHolder(private val binding: ItemEventBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(event: ListEventsItem) {
            binding.title.text = event.name

            // Tampilkan tanggal hanya jika beginTime dan endTime tidak null atau kosong
            if (!event.beginTime.isNullOrEmpty() && !event.endTime.isNullOrEmpty()) {
                binding.date.text = "${event.beginTime} - ${event.endTime}"
                binding.date.visibility = View.VISIBLE
            } else {
                // Sembunyikan teks tanggal jika nilainya null atau kosong
                binding.date.visibility = View.GONE
            }

            Glide.with(itemView.context)
                .load(event.mediaCover)
                .into(binding.image)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ListEventsItem>() {
            override fun areItemsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ListEventsItem, newItem: ListEventsItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}