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
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

    private lateinit var viewModel: SplashViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = SplashFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(
            this, SplashViewModelFactory(
                Repository.getInstance(
                    localSource = ConcreteLocalSource.getInstance(requireContext()),
                    preferences = PreferenceProvider(requireContext())
                )
            )
        )[SplashViewModel::class.java]
        requireActivity().lifecycleScope.launchWhenCreated {
            checkPermission()
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    private val requestPermissionLauncher =
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { result ->
            if (result[permissions[0]] == true && result[permissions[1]] == true)
                getLocation()
            else
                dinedPermission()
        }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), permissions[0]
            ) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                requireContext(), permissions[1]
            ) == PackageManager.PERMISSION_GRANTED
        )
            getLocation()
        else
            dinedPermission()
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
        showSnackbar()
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
            Status.ERROR -> {
                binding.retryBtn.isVisible = true
                binding.txtHint.isVisible = true
                binding.animationView.isVisible = true
                binding.animationView.setAnimation(R.raw.connection_error)
                binding.txtHint.text = res.message
                binding.animationView.pauseAnimation()
                binding.animationView.setPadding(40, 0, 40, 0)
                binding.retryBtn.setOnClickListener {
                    getLocation()
                }
            }
            Status.LOADING -> {
                binding.retryBtn.isVisible = false
                binding.txtHint.isVisible = false
                binding.animationView.setPadding(0, 0, 0, 0)
                binding.animationView.setAnimation(R.raw.newspl)
                binding.animationView.resumeAnimation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun getLocation() {
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            if (it != null)
                saveLocation(LatLng(it.latitude, it.longitude))
            else
                requestLocation()
        }

    }

    private fun requestLocation() {
        fusedLocationProviderClient.requestLocationUpdates(
            LocationRequest().apply {
                interval = 1000
                fastestInterval = 5000
                priority = LocationRequest.PRIORITY_LOW_POWER
            },
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationRequest: LocationResult?) {
            locationRequest ?: return
            val latLng = LatLng(
                locationRequest.lastLocation.latitude,
                locationRequest.lastLocation.longitude
            )
            saveLocation(latLng)
            fusedLocationProviderClient.removeLocationUpdates(this)
        }
    }

    private fun saveLocation(latLng: LatLng) {
        viewModel.setLatLon(latLng)
        if (view != null)
            viewModel.getDataFromRepo(latLng, viewModel.getLang())
                .observe(viewLifecycleOwner) { res ->
                    onResponse(res)
                    fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                }
    }

    private fun showSnackbar(){
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

}