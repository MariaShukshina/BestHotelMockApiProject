package com.example.besthoteldemoproject.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.core.content.ContextCompat
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

        binding.etPhoneNumber.addTextChangedListener(object : TextWatcher {
            private var beforePhone: CharSequence = ""

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                beforePhone = s!!.filter { it.isDigit() }
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                var result: String = getString(R.string.number_mask);
                if (s.isNullOrEmpty()) {
                    binding.etPhoneNumber.removeTextChangedListener(this)
                    binding.etPhoneNumber.setText(result)
                    binding.etPhoneNumber.setSelection(result.length)
                    binding.etPhoneNumber.addTextChangedListener(this)
                    return
                }
                var phoneNumber = s.filter { it.isDigit() }
                if (s.length < result.length) {
                    if (phoneNumber.length == beforePhone.length) {
                        phoneNumber = if (phoneNumber.length < 2) {
                            " "
                        } else {
                            phoneNumber.removeRange(
                                phoneNumber.length - (result.length - s.length),
                                phoneNumber.length
                            )
                        }
                    }
                    phoneNumber = phoneNumber.removeRange(0, 1)
                } else {
                    phoneNumber = phoneNumber.removeRange(0, 1)
                    if (phoneNumber.isNotEmpty() && (phoneNumber[0] == '7' || phoneNumber[0] == '8')) {
                        phoneNumber = if (phoneNumber.length > 1 && phoneNumber[1] == '9') {
                            phoneNumber.removeRange(0, 1)
                        } else {
                            "9" + phoneNumber.removeRange(0, 1);
                        }
                    }
                }
                if (phoneNumber.isNotEmpty()) {
                    var i = 0;
                    var x = 0;
                    while (x < phoneNumber.length && i < result.length) {
                        if (result[i] == '*') {
                            result = result.replaceFirst(result[i], phoneNumber[x])
                            x += 1;
                        }
                        i += 1;
                    }
                }
                binding.etPhoneNumber.removeTextChangedListener(this)
                binding.etPhoneNumber.setText(result)
                binding.etPhoneNumber.setSelection(result.length)
                binding.etPhoneNumber.addTextChangedListener(this)
            }
        })

        binding.emailEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            val invalidColor = ContextCompat.getColor(requireContext(), R.color.email_invalid)
            val validColor = ContextCompat.getColor(requireContext(), R.color.valid_input)
            if (!hasFocus) {
                if (!isValidEmail(binding.emailEditText.text) && !TextUtils.isEmpty(binding.emailEditText.text)) {
                    binding.emailEditText.setBackgroundColor(invalidColor)
                } else {
                    binding.emailEditText.setBackgroundColor(validColor)
                }
            }
        }

        val scrollView = binding.bookingScrollView
        var currentScrollY = 0

        binding.firstTouristArrowUp.setOnClickListener {

            currentScrollY = scrollView.scrollY

            binding.firstTouristInfoLayout.visibility = View.GONE
            binding.firstTouristArrowDown.visibility = View.VISIBLE
            binding.firstTouristArrowUp.visibility = View.GONE

        }
        binding.firstTouristArrowDown.setOnClickListener {


            binding.firstTouristInfoLayout.visibility = View.VISIBLE
            binding.firstTouristArrowDown.visibility = View.GONE
            binding.firstTouristArrowUp.visibility = View.VISIBLE

            scrollView.post {
                scrollView.scrollTo(0, currentScrollY)
            }
        }
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
                binding.selectedDatesTextView.text =
                    getString(R.string.selectedDates, it.tour_date_start, it.tour_date_stop)
                binding.selectedNightsNumberTextView.text =
                    getString(R.string.selectedNightsNumber, it.number_of_nights)
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