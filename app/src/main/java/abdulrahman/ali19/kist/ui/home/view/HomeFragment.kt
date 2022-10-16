package abdulrahman.ali19.kist.ui.home.view

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import abdulrahman.ali19.kist.R
import abdulrahman.ali19.kist.data.local.ConcreteLocalSource
import abdulrahman.ali19.kist.data.preferences.PreferenceProvider
import abdulrahman.ali19.kist.data.remote.ConnectionProvider
import abdulrahman.ali19.kist.data.remote.Resource
import abdulrahman.ali19.kist.data.remote.Status
import abdulrahman.ali19.kist.databinding.FragmentHomeBinding
import abdulrahman.ali19.kist.pojo.model.HomeModel
import abdulrahman.ali19.kist.pojo.model.dbentities.CashedEntity
import abdulrahman.ali19.kist.pojo.model.weather.Hourly
import abdulrahman.ali19.kist.pojo.model.weather.WeatherResponse
import abdulrahman.ali19.kist.pojo.repo.Repository
import abdulrahman.ali19.kist.ui.home.viewmodel.HomeViewModel
import abdulrahman.ali19.kist.ui.home.viewmodel.HomeViewModelFactory
import abdulrahman.ali19.kist.util.getDate

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {

    private lateinit var navController: NavController
    private val args: HomeFragmentArgs by navArgs()
    private lateinit var binding: FragmentHomeBinding
    private lateinit var cashedData: WeatherResponse

    private val viewModel:HomeViewModel by viewModels {
        HomeViewModelFactory(
            Repository.getInstance(
                localSource = ConcreteLocalSource.getInstance(requireContext()),
                preferences = PreferenceProvider(requireContext())
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        val data = args.data

        if (data != null) {
            setupLayout(data)
        } else {
            viewModel.getCashedData().observe(viewLifecycleOwner) { checkStatus(it) }
        }

        binding.btnViewFullReport.setOnClickListener {
            navController
                .navigate(
                    HomeFragmentDirections.actionNavHomeToWeekReportFragment(
                        args.data ?: cashedData
                    )
                )
        }
    }

    private fun checkStatus(it: Resource<List<CashedEntity>>) {
        when (it.status) {
            Status.SUCCESS -> if (!it.data.isNullOrEmpty()) setupLayout(it.data[0].cashedData)
            Status.ERROR -> Log.d(TAG, "onViewCreated: ${it.message}")
            Status.LOADING -> Log.d(TAG, "onViewCreated: LOADING !!")
        }

    }

    private fun setupLayout(weatherResponse: WeatherResponse) {
        weatherResponse.apply {
            cashedData = this
            binding.data = HomeModel(
                timezone,
                getDate(current.dt),
                current.weather[0].icon,
                current.temp,
                current.weather[0].description,
                current.windSpeed,
                current.humidity,
                current.feelsLike,
                hourly as ArrayList<Hourly>
            )
            binding.latlog = args.latlog
            binding.executePendingBindings()
        }
    }

    /*override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
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
    }*/

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