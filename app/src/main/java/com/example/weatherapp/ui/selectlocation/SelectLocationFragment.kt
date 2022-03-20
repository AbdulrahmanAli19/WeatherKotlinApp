package com.example.weatherapp.ui.selectlocation

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
import com.example.weatherapp.pojo.model.dbentities.FavoriteEntity
import com.example.weatherapp.pojo.repo.Repository
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
    private lateinit var fab: ExtendedFloatingActionButton
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
    ): View? {
        return inflater.inflate(R.layout.fragment_select_location, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        fab = view.findViewById(R.id.btnSave)
        fab.setOnClickListener {
            viewModel.saveMyLatLon(latLng)
            navController.popBackStack()
            Snackbar.make(view, "Saved :)", Snackbar.LENGTH_SHORT).show()
        }
    }

    override fun onMapClick(latLng: LatLng) {
        fab.visibility = View.VISIBLE
        map.clear()
        map.addMarker(MarkerOptions().position(latLng))

        if (args.itItMyLocation) {
            this.latLng = latLng
            if (viewModel.getMyLatLon() == nullLatLon)
                fab.text = getString(R.string.update)
        } else {
            val geocoder = Geocoder(requireContext().applicationContext, Locale.getDefault())

            val address: MutableList<Address>? =
                geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

            if (address != null)
                Log.d(TAG, "onMapClick: ${address[0].countryName}")

        }

    }

}