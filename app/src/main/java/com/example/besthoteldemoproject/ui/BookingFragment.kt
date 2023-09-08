package com.example.besthoteldemoproject.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.besthoteldemoproject.R
import com.example.besthoteldemoproject.databinding.FragmentBookingBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookingFragment : Fragment() {

    private lateinit var binding: FragmentBookingBinding
    private val viewModel by viewModel<BookingFragmentViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.bookingToolbar.setupWithNavController(navController, appBarConfiguration)

        observeBookingData()

    }

    private fun observeBookingData() {
        viewModel.bookingResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.starNumberTextView.text = it.horating.toString()
                binding.ratingTextView.text = it.rating_name
                binding.hotelNameTextView.text = it.hotel_name
                binding.addressTextView.text = it.hotel_adress
                binding.cityTextView.text = it.departure
                binding.selectedCountryCityTextView.text = it.arrival_country
                binding.selectedDatesTextView.text = getString(R.string.selectedDates, it.tour_date_start, it.tour_date_stop)
                binding.selectedNightsNumberTextView.text = getString(R.string.selectedNightsNumber, it.number_of_nights)
                binding.selectedHotelNameTextView.text = it.hotel_name
                binding.selectedRoomTextView.text = it.room
                binding.foodInfoTextView.text = it.nutrition
            }
        }
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return !target.isNullOrBlank() && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

}