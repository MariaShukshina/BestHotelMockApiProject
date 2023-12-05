package com.example.besthoteldemoproject.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.besthoteldemoproject.R
import com.example.besthoteldemoproject.databinding.FragmentBookingBinding
import com.example.besthoteldemoproject.presentation.models.Tourist
import com.google.android.material.snackbar.Snackbar
import org.koin.androidx.viewmodel.ext.android.viewModel


class BookingFragment : Fragment() {

    private lateinit var binding: FragmentBookingBinding
    private val viewModel by viewModel<BookingFragmentViewModel>()
    private val touristList = arrayListOf(Tourist(), Tourist())
    private lateinit var adapter: TouristRecyclerViewAdapter

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

        val invalidColor = ContextCompat.getColor(requireContext(), R.color.email_invalid)
        val validColor = ContextCompat.getColor(requireContext(), R.color.valid_input)

        adapter = TouristRecyclerViewAdapter(
            requireContext(),
            ::isValidInputString, ::isValidInputString, ::isValidInputString,
            ::isValidInputString, ::isValidInputString, ::isValidInputString
        )

        observeBookingData()

        setupRecyclerView()

        binding.etPhoneNumber.addTextChangedListener(object : TextWatcher {
            private var beforePhoneNumber: CharSequence = ""

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                beforePhoneNumber = s!!.filter { it.isDigit() }
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                var result: String = getString(R.string.number_mask)
                if (s.isNullOrEmpty()) {
                    binding.etPhoneNumber.removeTextChangedListener(this)
                    binding.etPhoneNumber.setText(result)
                    binding.etPhoneNumber.setSelection(result.length)
                    binding.etPhoneNumber.addTextChangedListener(this)
                    return
                }
                var phoneNumber = s.filter { it.isDigit() }
                if (s.length < result.length) {
                    if (phoneNumber.length == beforePhoneNumber.length) {
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
                            "9" + phoneNumber.removeRange(0, 1)
                        }
                    }
                }
                if (phoneNumber.isNotEmpty()) {
                    var i = 0
                    var x = 0
                    while (x < phoneNumber.length && i < result.length) {
                        if (result[i] == '*') {
                            result = result.replaceFirst(result[i], phoneNumber[x])
                            x += 1
                        }
                        i += 1
                    }
                }
                binding.etPhoneNumber.removeTextChangedListener(this)
                binding.etPhoneNumber.setText(result)
                binding.etPhoneNumber.setSelection(result.length)
                binding.etPhoneNumber.addTextChangedListener(this)
            }
        })

        binding.emailEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                changeEmailEditTextBackgroundColor(validColor, invalidColor)
            }
        }

        binding.etPhoneNumber.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                changePhoneEditTextBackgroundColor(validColor, invalidColor)
            }
        }

        binding.addTouristPlusIcon.setOnClickListener {
            for (i in 0 until adapter.itemCount) {
                saveTourist(i)
            }
            val nestedScrollView = binding.bookingScrollView
            touristList.add(Tourist())
            adapter.setList(touristList)
            nestedScrollView.requestLayout()
        }

        binding.payButton.setOnClickListener {
            adapter.setPayButtonClicked(true)
            var result = true
            for (i in 0 until adapter.itemCount) {
                saveTourist(i)

                if (result) {
                    result = isValidInputString(touristList[i].name) && isValidInputString(touristList[i].surname)
                                && isValidInputString(touristList[i].citizenship) && isValidInputString(
                            touristList[i].passportNumber) && isValidInputString(touristList[i].passportValidTill)
                                && isValidInputString(touristList[i].birthDay)
                }
            }

            if (result && isValidEmail(binding.emailEditText.text)
                && !binding.etPhoneNumber.text?.contains("*")!!) {
                navController.navigate(R.id.action_bookingFragment_to_successFragment)
            } else {
                changeEmailEditTextBackgroundColor(validColor, invalidColor)
                changePhoneEditTextBackgroundColor(validColor, invalidColor)
                Snackbar.make(view, "Заполните все поля", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun changePhoneEditTextBackgroundColor(validColor: Int, invalidColor: Int) {
        if (binding.etPhoneNumber.text?.contains("*")!!) {
            binding.etPhoneNumber.setBackgroundColor(invalidColor)
        } else {
            binding.etPhoneNumber.setBackgroundColor(validColor)
        }
    }

    private fun changeEmailEditTextBackgroundColor(validColor: Int, invalidColor: Int) {
        if (!isValidEmail(binding.emailEditText.text)) {
            binding.emailEditText.setBackgroundColor(invalidColor)
        } else {
            binding.emailEditText.setBackgroundColor(validColor)
        }
    }

    private fun saveTourist(i: Int) {
        val item = binding.touristRecyclerView.getChildAt(i)
        touristList[i].name = item.findViewById<EditText>(R.id.etNextName).text.toString()
        touristList[i].surname =
            item.findViewById<EditText>(R.id.etNextSurname).text.toString()
        touristList[i].citizenship =
            item.findViewById<EditText>(R.id.etCitizenship).text.toString()
        touristList[i].birthDay =
            item.findViewById<EditText>(R.id.etNextBirthDay).text.toString()
        touristList[i].passportNumber =
            item.findViewById<EditText>(R.id.etNextPassportNumber).text.toString()
        touristList[i].passportValidTill =
            item.findViewById<EditText>(R.id.etNextPassportValidTill).text.toString()

    }

    private fun setupRecyclerView() {
        adapter.setList(touristList)
        binding.touristRecyclerView.adapter = adapter
        binding.touristRecyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL, false
        )
    }

    private fun observeBookingData() {
        viewModel.bookingResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                val totalAmount = it.tour_price + it.fuel_charge + it.service_charge
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
                binding.tourPriceTextView.text = getString(R.string.tour_price, it.tour_price)
                binding.fuelChargeTextView.text = getString(R.string.fuelCharge, it.fuel_charge)
                binding.serviceChargeTextView.text =
                    getString(R.string.service_charge, it.service_charge)
                binding.amountTextView.text = getString(R.string.amount, totalAmount)
                binding.payButton.text = getString(R.string.pay_text, totalAmount)
            }
        }
    }

    private fun isValidEmail(target: CharSequence?): Boolean {
        return !target.isNullOrBlank() && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun isValidInputString(value: String): Boolean {
        return value.isNotEmpty()
    }
}