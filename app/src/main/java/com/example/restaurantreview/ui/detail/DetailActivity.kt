package com.example.restaurantreview.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.example.restaurantreview.data.local.entity.FavoriteEvent
import com.example.restaurantreview.data.remote.response.DetailEventResponse
import com.example.restaurantreview.ui.ViewModelFactory
import com.example.restaurantreview.R
import com.example.restaurantreview.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels {
        ViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val eventId = intent.getStringExtra(EXTRA_EVENT_ID) ?: return

        detailViewModel.getDetailEvent(eventId)

        detailViewModel.detailEvent.observe(this) { detailResponse: DetailEventResponse? ->
            if (detailResponse != null) {
                detailResponse.event?.let { event ->
                    binding.tvTitle.text = event.name ?: "Unknown Event"
                    binding.tvDate.text = event.beginTime
                    binding.tvCity.text = event.cityName
                    binding.tvDescription.text = HtmlCompat.fromHtml(event.description ?: "", HtmlCompat.FROM_HTML_MODE_LEGACY)
                    binding.tvOwnerName.text = "Penyelenggara: ${event.ownerName}"

                    Glide.with(this)
                        .load(event.imageLogo)
                        .into(binding.ivEventLogo)

                    val remainingQuota = (event.quota ?: 0) - (event.registrants ?: 0)
                    binding.tvRemainingQuota.text = "Sisa Kuota: $remainingQuota"
                    binding.btnOpenLink.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.link))
                        startActivity(intent)
                    }
                    binding.tvError.visibility = View.GONE

                    // Konfigurasi tombol favorite
                    configureFavoriteButton(event.id?.toString() ?: "0", event.name ?: "Unknown Event", event.mediaCover)
                }
            } else {
                binding.tvError.visibility = View.VISIBLE
                binding.tvError.text = "You're offline, turn on Internet Access yaa!"
            }
        }

        detailViewModel.isLoading.observe(this) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        // Observasi status favorite dan ubah ikon tombol favorite
        detailViewModel.isFavorite.observe(this) { isFavorite ->
            val icon = if (isFavorite) R.drawable.ic_favorite else R.drawable.ic_favorite_border
            binding.fabFavorite.setImageResource(icon)
        }
    }

    private fun configureFavoriteButton(eventId: String, eventName: String, mediaCover: String?) {
        binding.fabFavorite.setOnClickListener {
            if (detailViewModel.isFavorite.value == true) {
                // Hapus dari favorite
                val favoriteEvent = FavoriteEvent(eventId, eventName, mediaCover)
                detailViewModel.deleteFavoriteEvent(favoriteEvent)
            } else {
                // Tambahkan ke favorite
                val favoriteEvent = FavoriteEvent(eventId, eventName, mediaCover)
                detailViewModel.insertFavoriteEvent(favoriteEvent)
            }
        }
    }

    companion object {
        const val EXTRA_EVENT_ID = "extra_event_id"
    }
}