package com.example.restaurantreview.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantreview.data.remote.response.ListEventsItem
import com.example.restaurantreview.databinding.FragmentFavoriteBinding
import com.example.restaurantreview.ui.EventAdapter
import com.example.restaurantreview.ui.ViewModelFactory

class FavoriteFragment : Fragment() {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding get() = _binding!!
    private val favoriteViewModel: FavoriteViewModel by viewModels {
        ViewModelFactory.getInstance(requireContext())
    }
    private lateinit var eventAdapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()

        favoriteViewModel.getAllFavoriteEvents().observe(viewLifecycleOwner) { favoriteEvents ->
            // Map FavoriteEvent ke ListEventsItem
            val items = favoriteEvents.map {
                ListEventsItem(
                    id = it.id.toInt(),
                    name = it.name, // Menyediakan judul default jika kosong
                    mediaCover = it.mediaCover ?: "https://via.placeholder.com/150" // Gambar default jika kosong
                )
            }
            eventAdapter.submitList(items)
        }
    }

    private fun setupRecyclerView() {
        eventAdapter = EventAdapter()
        binding.rvFavoriteEvent.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = eventAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
