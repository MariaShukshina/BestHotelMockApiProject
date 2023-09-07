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
import com.example.besthoteldemoproject.R
import com.example.besthoteldemoproject.databinding.FragmentHotelBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Locale

class HotelFragment : Fragment() {

    private lateinit var binding: FragmentHotelBinding
    private val viewModel by viewModel<HotelFragmentViewModel>()

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
        observeHotelData(adapter)
        /*adapter.addItem("https://www.atorus.ru/sites/default/files/upload/image/News/56149/Club_Priv%C3%A9_by_Belek_Club_House.jpg")
        adapter.addItem("https://deluxe.voyage/useruploads/articles/The_Makadi_Spa_Hotel_02.jpg")
        adapter.addItem("https://deluxe.voyage/useruploads/articles/article_1eb0a64d00.jpg")*/
        sliderView.setSliderAdapter(adapter)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHotelBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun observeHotelData(adapter: SliderAdapter) {
        viewModel.hotelDataResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                for (i in it.image_urls) {
                    adapter.addItem(i)
                }
                binding.starNumberTextView.text = it.rating.toString()
                binding.ratingTextView.text = it.rating_name
                binding.hotelNameTextView.text = it.name
                binding.addressTextView.text = it.adress
                binding.priceTextView.text = getString(R.string.price_from, it.minimal_price)
                binding.priceDetailsView.text = it.price_for_it.lowercase(Locale.ROOT)
                binding.peculiarityTextView.text = it.about_the_hotel.peculiarities[0]
                binding.peculiarityTwoTextView.text = it.about_the_hotel.peculiarities[1]
                binding.peculiarityThreeTextView.text = it.about_the_hotel.peculiarities[2]
                binding.peculiarityFourTextView.text = it.about_the_hotel.peculiarities[3]
                binding.descriptionTextView.text = it.about_the_hotel.description

            }
        }
    }
}