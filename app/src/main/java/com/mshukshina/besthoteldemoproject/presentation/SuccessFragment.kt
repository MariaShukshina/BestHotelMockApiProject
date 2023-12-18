package com.mshukshina.besthoteldemoproject.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.mshukshina.besthoteldemoproject.R
import com.mshukshina.besthoteldemoproject.databinding.FragmentSuccessBinding

class SuccessFragment : Fragment() {

    private lateinit var binding: FragmentSuccessBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSuccessBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        binding.successToolbar.setupWithNavController(navController, appBarConfiguration)

        val randomInt = (100000..200000).random()

        binding.confirmationTextView.text = getString(R.string.confirmation_text, randomInt)

        binding.successButton.setOnClickListener {
            navController.popBackStack(R.id.hotelFragment, false)
        }
    }

}