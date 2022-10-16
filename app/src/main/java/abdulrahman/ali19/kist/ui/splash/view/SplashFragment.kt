package abdulrahman.ali19.kist.ui.splash.view

import abdulrahman.ali19.kist.R
import abdulrahman.ali19.kist.data.local.ConcreteLocalSource
import abdulrahman.ali19.kist.data.preferences.PreferenceProvider
import abdulrahman.ali19.kist.data.remote.Resource
import abdulrahman.ali19.kist.data.remote.Status
import abdulrahman.ali19.kist.databinding.SplashFragmentBinding
import abdulrahman.ali19.kist.pojo.model.weather.WeatherResponse
import abdulrahman.ali19.kist.pojo.repo.Repository
import abdulrahman.ali19.kist.ui.splash.viewmodel.SplashViewModel
import abdulrahman.ali19.kist.ui.splash.viewmodel.SplashViewModelFactory
import abdulrahman.ali19.kist.util.isNetworkConnected
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Looper
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
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieDrawable
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.snackbar.Snackbar


private const val TAG = "SplashFragment.dev"

class SplashFragment : Fragment() {

    private val permissions: MutableList<String> = arrayListOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    private lateinit var binding: SplashFragmentBinding
    private val fusedLocationProviderClient by lazy {
        FusedLocationProviderClient(requireActivity())
    }

    private val viewModel: SplashViewModel by viewModels {
        SplashViewModelFactory(
            Repository.getInstance(
                localSource = ConcreteLocalSource.getInstance(requireContext()),
                preferences = PreferenceProvider(requireContext())
            )
        )
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
            findNavController().navigate(
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
                findNavController().navigate(
                    SplashFragmentDirections.actionSplashFragmentToNavHome(
                        data = res.data ?: WeatherResponse(),
                        latlog = viewModel.getLatLon()
                    )
                )
            }
            Status.ERROR -> Log.d(TAG, "onViewCreated: ${res.message}")
            Status.LOADING -> Log.d(TAG, "onViewCreated: loading")
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            saveLocation(it)
        }.addOnFailureListener {
            fusedLocationProviderClient.requestLocationUpdates(
                LocationRequest().apply {
                    interval = 4000
                    fastestInterval = 5000
                    priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
                },
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationRequest: LocationResult?) {
            locationRequest ?: return
            saveLocation(locationRequest.lastLocation)
            fusedLocationProviderClient.removeLocationUpdates(this)
        }
    }

    private fun saveLocation(lastLocation: Location?) {
        lastLocation ?: return
        val latLng = LatLng(lastLocation.latitude, lastLocation.longitude)
        viewModel.setLatLon(latLng)
        viewModel.getDataFromRepo(latLng, viewModel.getLang())
            .observe(viewLifecycleOwner) { res ->
                onResponse(res)
            }
    }

}