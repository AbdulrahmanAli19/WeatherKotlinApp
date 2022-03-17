package com.example.weatherapp.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
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
    private val args: HomeFragmentArgs by navArgs()
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        setAppLayout(args.data)
        binding.btnViewFullReport.setOnClickListener {
            navController.popBackStack()
          //  navController.navigate(HomeFragmentDirections.actionNavHomeToWeekReportFragment())
        }
    }

    private fun setAppLayout(it: WeatherResponse?) {
        binding.data = HomeModel(it?.timezone ?: "",
            getDate(it?.current?.dt ?: 0),
            it?.current?.weather?.get(0)?.icon ?: "",
            it?.current?.temp ?: 0.0,
            it?.current?.weather?.get(0)?.description ?: "",
            it?.current?.windSpeed ?: 0.0,
            it?.current?.humidity ?: 0,
            it?.current?.feelsLike ?: 0.0,
            it?.hourly as ArrayList<Hourly>
        )
        binding.executePendingBindings()
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