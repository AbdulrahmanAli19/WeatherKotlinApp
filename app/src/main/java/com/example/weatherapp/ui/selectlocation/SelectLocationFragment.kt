package com.example.weatherapp.ui.selectlocation

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.R
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

class SelectLocationFragment() : Fragment(), GoogleMap.OnMapClickListener {
    private lateinit var pref: SharedPreferences
    private lateinit var map: GoogleMap
    private lateinit var latLng: LatLng
    private lateinit var fab: ExtendedFloatingActionButton
    private lateinit var navController: NavController

    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        with(map) {
            setOnMapClickListener(this@SelectLocationFragment)
            uiSettings.setAllGesturesEnabled(true)

            val latLng: LatLng = if (pref.getString("myLat", "empty").equals("empty")) {
                LatLng(30.02401127333763, 31.564412713050846)

            } else {
                LatLng(
                    pref.getString("myLat", "empty")!!.toDouble(),
                    pref.getString("myLon", "empty")!!.toDouble()
                )
            }

            googleMap.addMarker(MarkerOptions().position(latLng).title("My Home"))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        pref = requireContext().getSharedPreferences(
            getString(R.string.pref_name),
            Context.MODE_PRIVATE
        )
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
            val prefEditor: SharedPreferences.Editor = pref.edit()
            prefEditor.putString("myLat", latLng.latitude.toString())
            prefEditor.putString("myLon", latLng.longitude.toString())
            prefEditor.apply()
            navController.popBackStack()
            Snackbar.make(view, "Saved :)", Snackbar.LENGTH_SHORT).show()
        }
    }

    @SuppressLint("CommitPrefEdits")
    override fun onMapClick(latLng: LatLng) {
        fab.visibility = View.VISIBLE
        map.clear()
        map.addMarker(MarkerOptions().position(latLng))
        val geocoder = Geocoder(requireContext().applicationContext, Locale.getDefault())
        val address: MutableList<Address>? =
            geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)

        if (address == null) {
            Log.d(TAG, "onMapClick: watting for location name")
        } else {
            Log.d(TAG, "onMapClick: ${address.get(0).countryName}")
        }
        this.latLng = latLng
        if (!pref.getString("myLat", "empty").equals("empty"))
            fab.text = getString(R.string.update)
    }

}