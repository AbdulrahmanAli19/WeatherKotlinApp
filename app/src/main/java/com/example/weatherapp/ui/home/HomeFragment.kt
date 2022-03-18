package com.example.weatherapp.ui.home

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.pojo.model.HomeModel
import com.example.weatherapp.pojo.model.weather.Hourly
import com.example.weatherapp.pojo.model.weather.WeatherResponse
import com.example.weatherapp.util.getDate

class HomeFragment : Fragment() {

    private lateinit var navController: NavController
    private val args: HomeFragmentArgs by navArgs()
    private lateinit var binding: FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        setAppLayout(args.data)
        binding.btnViewFullReport.setOnClickListener {
            navController
                .navigate(
                    HomeFragmentDirections.actionNavHomeToWeekReportFragment(
                        args.data ?: WeatherResponse()
                    )
                )
        }
    }

    private fun setAppLayout(it: WeatherResponse?) {
        binding.data = HomeModel(
            it?.timezone ?: "",
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_location -> navController.navigate(
                HomeFragmentDirections
                    .actionNavHomeToSelectLocationFragment()
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