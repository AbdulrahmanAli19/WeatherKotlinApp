package com.example.weatherapp.ui.selectlocation.view

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weatherapp.R
import com.example.weatherapp.data.local.ConcreteLocalSource
import com.example.weatherapp.data.preferences.NULL_LAT
import com.example.weatherapp.data.preferences.NULL_LON
import com.example.weatherapp.data.preferences.PreferenceProvider
import com.example.weatherapp.data.remote.ConnectionProvider
import com.example.weatherapp.data.remote.Resource
import com.example.weatherapp.data.remote.Status
import com.example.weatherapp.databinding.FragmentSelectLocationBinding
import com.example.weatherapp.pojo.model.dbentities.FavoriteEntity
import com.example.weatherapp.pojo.model.weather.WeatherResponse
import com.example.weatherapp.pojo.repo.Repository
import com.example.weatherapp.ui.selectlocation.SelectLocationFragmentArgs
import com.example.weatherapp.ui.selectlocation.viewmodel.SelectLocationViewModel
import com.example.weatherapp.ui.selectlocation.viewmodel.SelectLocationViewModelFactory
import com.example.weatherapp.ui.splash.viewmodel.SplashViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.snackbar.Snackbar
import java.util.*

private const val TAG = "SelectLocationFragment"

class SelectLocationFragment : Fragment(), GoogleMap.OnMapClickListener {

    private val nullLatLon = LatLng(NULL_LAT, NULL_LON)
    private lateinit var map: GoogleMap
    private lateinit var latLng: LatLng
    private lateinit var binding: FragmentSelectLocationBinding
    private val args: SelectLocationFragmentArgs by navArgs()
    private lateinit var navController: NavController

    private val viewModel by viewModels<SelectLocationViewModel> {
        SelectLocationViewModelFactory(
            Repository.getInstance(
                remoteSource = ConnectionProvider,
                localSource = ConcreteLocalSource.getInstance(requireContext()),
                preferences = PreferenceProvider(requireContext())
            )
        )
    }

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        with(map) {
            setOnMapClickListener(this@SelectLocationFragment)
            uiSettings.setAllGesturesEnabled(true)
            googleMap.addMarker(MarkerOptions().position(viewModel.getMyLatLon()).title("My Home"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(viewModel.getMyLatLon(), 10f))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectLocationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

    }

    override fun onMapClick(latLng: LatLng) {
        map.clear()
        map.addMarker(MarkerOptions().position(latLng))

        if (args.itItMyLocation) {
            this.latLng = latLng
            if (viewModel.getMyLatLon() != nullLatLon)
                binding.btnSave.text = getString(R.string.update)

            binding.btnSave.setOnClickListener {
                viewModel.saveMyLatLon(latLng)
                navController.popBackStack()
                Snackbar.make(requireView(), "Saved :)", Snackbar.LENGTH_SHORT).show()
            }
        } else {
            val geocoder = Geocoder(requireContext().applicationContext, Locale.getDefault())
            val address: MutableList<Address>? =
                geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            viewModel.getWeatherRemotlyLatlon(latLng).observe(viewLifecycleOwner) {
                when (it.status) {
                    Status.LOADING -> {
                        Log.d(TAG, "addLocationToDB: called")
                        map.setOnMapClickListener(null)
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        map.setOnMapClickListener(this)
                        if (address != null)
                            viewModel.addFavoriteTodatabase(
                                FavoriteEntity(
                                    locationName = address[0].countryName,
                                    latLng = latLng,
                                    cashedData = it.data!!
                                )
                            )
                        binding.progressBar.visibility = View.GONE
                        binding.btnSave.visibility = View.VISIBLE
                        binding.btnSave.setOnClickListener {
                            navController.popBackStack()
                            Snackbar.make(requireView(), "Saved :)", Snackbar.LENGTH_SHORT).show()
                        }
                    }
                    Status.ERROR -> {
                        Log.d(TAG, "onMapClick: ")
                    }
                }
            }
        }

    }



}