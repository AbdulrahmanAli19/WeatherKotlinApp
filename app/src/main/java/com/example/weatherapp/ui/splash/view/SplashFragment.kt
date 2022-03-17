package com.example.weatherapp.ui.splash.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.data.remote.Resource
import com.example.weatherapp.data.remote.Status
import com.example.weatherapp.databinding.SplashFragmentBinding
import com.example.weatherapp.pojo.model.weather.WeatherResponse
import com.example.weatherapp.ui.splash.viewmodel.SplashViewModel
import com.example.weatherapp.ui.splash.viewmodel.SplashViewModelFactory

class SplashFragment() : Fragment() {

    private val TAG = "SplashFragment.dev"

    private lateinit var navController: NavController
    private lateinit var binding: SplashFragmentBinding
    private val viewModel by viewModels<SplashViewModel> { SplashViewModelFactory(requireContext()) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        viewModel.getData().observe(viewLifecycleOwner) { onResponse(it) }
    }

    private fun onResponse(res: Resource<WeatherResponse>) {
        when (res.status) {
            Status.SUCCESS -> navController.navigate(
                SplashFragmentDirections.actionSplashFragmentToNavHome(
                    res.data ?: WeatherResponse()
                )
            )
            Status.ERROR -> Log.d(TAG, "onViewCreated: ${res.message}")
            Status.LOADING -> Log.d(TAG, "onViewCreated: loading")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = SplashFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


}