package com.example.weatherapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherapp.data.remote.Resource
import com.example.weatherapp.data.remote.Status
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.pojo.model.HomeModel
import com.example.weatherapp.pojo.model.weather.Hourly
import com.example.weatherapp.pojo.model.weather.WeatherResponse
import com.example.weatherapp.ui.home.viewmodel.HomeViewModel
import com.example.weatherapp.ui.home.viewmodel.HomeViewModelFactory
import com.example.weatherapp.util.getDate

class HomeFragment : Fragment() {
    private val TAG = "HomeFragment"
    private var _binding: FragmentHomeBinding? = null
    private lateinit var navController: NavController
    private val homeViewModel by viewModels<HomeViewModel> { HomeViewModelFactory(requireContext()) }
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        homeViewModel.getData().observe(viewLifecycleOwner) {
            it?.let { setAppLayout(it) }
        }
        binding.btnViewFullReport.setOnClickListener {
            navController.navigate(HomeFragmentDirections.actionNavHomeToWeekReportFragment())
        }
    }

    private fun setAppLayout(it: Resource<WeatherResponse>) {
        when (it.status) {
            Status.SUCCESS -> {
                binding.data = HomeModel(
                    it.data?.timezone ?: "",
                    getDate(it.data?.current?.dt ?: 0),
                    it.data?.current?.weather?.get(0)?.icon ?: "",
                    it.data?.current?.temp ?: 0.0,
                    it.data?.current?.weather?.get(0)?.description ?: "",
                    it.data?.current?.windSpeed ?: 0.0,
                    it.data?.current?.humidity ?: 0,
                    it.data?.current?.feelsLike ?: 0.0,
                    it.data?.hourly as ArrayList<Hourly>
                )
                binding.executePendingBindings()
            }
            Status.ERROR -> Log.d(TAG, "onViewCreated: ${it.message}")
            Status.LOADING -> Log.d(TAG, "onViewCreated: loading")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}