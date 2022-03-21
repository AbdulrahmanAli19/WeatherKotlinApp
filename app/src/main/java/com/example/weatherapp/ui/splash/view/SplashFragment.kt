package com.example.weatherapp.ui.splash.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.example.weatherapp.R
import com.example.weatherapp.data.local.ConcreteLocalSource
import com.example.weatherapp.data.preferences.PreferenceProvider
import com.example.weatherapp.data.remote.ConnectionProvider
import com.example.weatherapp.data.remote.Resource
import com.example.weatherapp.data.remote.Status
import com.example.weatherapp.databinding.SplashFragmentBinding
import com.example.weatherapp.pojo.model.weather.WeatherResponse
import com.example.weatherapp.pojo.repo.Repository
import com.example.weatherapp.ui.splash.viewmodel.SplashViewModel
import com.example.weatherapp.ui.splash.viewmodel.SplashViewModelFactory
import com.example.weatherapp.util.isNetworkConnected
import com.example.weatherapp.worker.CreateNitification
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar
import java.util.*


private const val TAG = "SplashFragment.dev"

class SplashFragment : Fragment() {

    private lateinit var locationManager: LocationManager
    lateinit var location: LocationListener
    private val permissions: MutableList<String> = arrayListOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private lateinit var navController: NavController
    private lateinit var binding: SplashFragmentBinding

    private val viewModel by viewModels<SplashViewModel> {
        SplashViewModelFactory(
            Repository.getInstance(
                remoteSource = ConnectionProvider,
                localSource = ConcreteLocalSource.getInstance(requireContext()),
                preferences = PreferenceProvider(requireContext())
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val languageToLoad = viewModel.getLang()
        val locale = Locale(languageToLoad)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        requireContext().resources.updateConfiguration(
            config,
            requireContext().resources.displayMetrics
        )

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = SplashFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity?.lifecycleScope?.launchWhenCreated {
            requestPermission()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if (it[permissions[0]] == true && it[permissions[1]] == true)
                if (isNetworkConnected(requireContext())) {
                    getLocation()
                    Log.d(TAG, "connected: ")
                } else
                    Log.d(TAG, ": notConnected!")
            else
                dinedPermission()
        }

    private fun requestPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(), permissions[0]
            ) == PackageManager.PERMISSION_GRANTED -> {
                if (ContextCompat.checkSelfPermission(
                        requireContext(), permissions[1]
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    if (isNetworkConnected(requireContext())) {
                        getLocation()
                        Log.d(TAG, "connected: ")
                    } else
                        Log.d(TAG, ": notConnected!")

                } else requestPermissionLauncher.launch(permissions.toTypedArray())
            }

            ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) -> dinedPermission()

            else -> requestPermissionLauncher.launch(permissions.toTypedArray())
        }
    }

    private fun dinedPermission() {
        binding.txtHint.visibility = View.VISIBLE
        binding.animationView.repeatCount = 1
        binding.animationView.setAnimation(R.raw.select_location)
        binding.animationView.setOnClickListener {
            navController.navigate(
                SplashFragmentDirections.actionNavSplashToSelectLocationFragment(
                    true
                )
            )
        }
        Snackbar.make(
            binding.parent,
            getString(R.string.premission_dined),
            Snackbar.LENGTH_INDEFINITE
        ).setAction(getString(R.string.ok)) {
            binding.txtHint.visibility = View.GONE
            binding.animationView.setAnimation(R.raw.newspl)
            binding.animationView.repeatCount = LottieDrawable.INFINITE
            requestPermissionLauncher.launch(permissions.toTypedArray())
        }.show()
    }

    @SuppressLint("MissingPermission")
    private fun onResponse(res: Resource<WeatherResponse>) {
        when (res.status) {
            Status.SUCCESS -> {
                viewModel.saveResponse(res.data ?: WeatherResponse())
                viewModel.setTimeStamp(System.currentTimeMillis())
                navController.navigate(
                    SplashFragmentDirections.actionSplashFragmentToNavHome(
                        res.data ?: WeatherResponse()
                    )
                )
            }
            Status.ERROR -> Log.d(TAG, "onViewCreated: ${res.message}")
            Status.LOADING -> Log.d(TAG, "onViewCreated: loading")
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        location = LocationListener()
        locationManager = requireContext().getSystemService(LOCATION_SERVICE) as LocationManager
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 1f, location)
    }

    inner class LocationListener : android.location.LocationListener {
        override fun onLocationChanged(p0: Location) {
            val latLng = LatLng(p0.latitude, p0.longitude)
            Log.d(TAG, ": $latLng")
            viewModel.setLatLon(latLng)
            viewModel.getDataFromRepo(latLng, viewModel.getLang())
                .observe(viewLifecycleOwner) { res ->
                    onResponse(res)
                }
            p0.removeAccuracy()
            locationManager.removeUpdates(location)
        }

        override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}

    }
}