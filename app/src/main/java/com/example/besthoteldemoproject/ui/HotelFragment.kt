package com.example.besthoteldemoproject.ui

import SliderAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.besthoteldemoproject.databinding.FragmentHotelBinding

class HotelFragment : Fragment() {

    private lateinit var binding: FragmentHotelBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)

        val sliderView = binding.imageSlider
        val adapter = SliderAdapter()
        adapter.addItem("https://www.atorus.ru/sites/default/files/upload/image/News/56149/Club_Priv%C3%A9_by_Belek_Club_House.jpg")
        adapter.addItem("https://deluxe.voyage/useruploads/articles/The_Makadi_Spa_Hotel_02.jpg")
        adapter.addItem("https://deluxe.voyage/useruploads/articles/article_1eb0a64d00.jpg")
        sliderView.setSliderAdapter(adapter)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHotelBinding.inflate(inflater, container, false)
        return binding.root
    }
}