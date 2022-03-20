package com.example.weatherapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.data.local.ConcreteLocalSource
import com.example.weatherapp.data.preferences.PreferenceProvider
import com.example.weatherapp.data.remote.ConnectionProvider
import com.example.weatherapp.data.remote.Resource
import com.example.weatherapp.data.remote.Status
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.pojo.model.HomeModel
import com.example.weatherapp.pojo.model.dbentities.CashedEntity
import com.example.weatherapp.pojo.model.weather.Hourly
import com.example.weatherapp.pojo.model.weather.WeatherResponse
import com.example.weatherapp.pojo.repo.Repository
import com.example.weatherapp.ui.home.viewmodel.HomeViewModel
import com.example.weatherapp.ui.home.viewmodel.HomeViewModelFactory
import com.example.weatherapp.util.getDate

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private lateinit var navController: NavController
    private val args: HomeFragmentArgs by navArgs()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var cashedData: WeatherResponse
    private val viewModel by viewModels<HomeViewModel> {
        HomeViewModelFactory(
            Repository.getInstance(
                remoteSource = ConnectionProvider,
                localSource = ConcreteLocalSource.getInstance(requireContext()),
                preferences = PreferenceProvider(requireContext())
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        viewModel.getCashedData().observe(viewLifecycleOwner) { setAppLayout(it) }
        binding.btnViewFullReport.setOnClickListener {
            navController
                .navigate(
                    HomeFragmentDirections.actionNavHomeToWeekReportFragment(
                        args.data ?: cashedData
                    )
                )
        }
    }

    private fun setAppLayout(it: Resource<List<CashedEntity>>) {
        when (it.status) {
            Status.SUCCESS -> {
                if (!it.data.isNullOrEmpty())
                    it.data.get(0).cashedData.apply {
                        cashedData = this
                        binding.data = HomeModel(
                            timezone,
                            getDate(current.dt),
                            current.weather.get(0).icon,
                            current.temp,
                            current.weather.get(0).description,
                            current.windSpeed,
                            current.humidity,
                            current.feelsLike,
                            hourly as ArrayList<Hourly>
                        )
                        binding.executePendingBindings()
                    }
            }
            Status.ERROR -> Log.d(TAG, "onViewCreated: ${it.message}")
            Status.LOADING -> Log.d(TAG, "onViewCreated: loading")
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_location -> navController.navigate(
                HomeFragmentDirections
                    .actionNavHomeToSelectLocationFragment(true)
            )
        }
        return false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        return binding.root
    }

}