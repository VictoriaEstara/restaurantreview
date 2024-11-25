package com.example.restaurantreview.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.restaurantreview.databinding.FragmentDashboardBinding
import com.example.restaurantreview.ui.EventAdapter
import com.example.restaurantreview.ui.MainViewModel

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!
    private lateinit var eventAdapter: EventAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Buat ViewModel
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        // Buat Adapter
        eventAdapter = EventAdapter()

        // Atur buat RecyclerView
        binding.rvEvent.layoutManager = LinearLayoutManager(requireContext())
        binding.rvEvent.adapter = eventAdapter

        // Observe LiveData buat data Event
        mainViewModel.getEvents(0)
        mainViewModel.eventResponse.observe(viewLifecycleOwner) { eventResponse ->
            eventAdapter.submitList(eventResponse.listEvents)
        }

        mainViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}