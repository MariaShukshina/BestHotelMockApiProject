package com.example.besthoteldemoproject.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.besthoteldemoproject.databinding.FragmentRoomBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RoomFragment : Fragment() {

    private lateinit var binding: FragmentRoomBinding
    private val viewModel by viewModel<RoomFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoomBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        val adapter = RoomsRecyclerViewAdapter(requireActivity())
        binding.roomsRecyclerView.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL, false)
        binding.roomsRecyclerView.adapter = adapter
        observeRoomData(adapter)
    }

    private fun observeRoomData(adapter: RoomsRecyclerViewAdapter) {
        viewModel.roomDataResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setRoomList(it.rooms)
            }
        }
    }
}