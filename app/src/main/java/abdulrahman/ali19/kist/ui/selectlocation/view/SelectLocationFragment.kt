package abdulrahman.ali19.kist.ui.selectlocation.view

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
import abdulrahman.ali19.kist.R
import abdulrahman.ali19.kist.data.local.ConcreteLocalSource
import abdulrahman.ali19.kist.data.preferences.NULL_LAT
import abdulrahman.ali19.kist.data.preferences.NULL_LON
import abdulrahman.ali19.kist.data.preferences.PreferenceProvider
import abdulrahman.ali19.kist.data.remote.ConnectionProvider
import abdulrahman.ali19.kist.data.remote.Status
import abdulrahman.ali19.kist.databinding.FragmentSelectLocationBinding
import abdulrahman.ali19.kist.pojo.model.dbentities.FavoriteEntity
import abdulrahman.ali19.kist.pojo.repo.Repository
import abdulrahman.ali19.kist.ui.selectlocation.viewmodel.SelectLocationViewModel
import abdulrahman.ali19.kist.ui.selectlocation.viewmodel.SelectLocationViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
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
            binding.btnSave.visibility = View.VISIBLE
            this.latLng = latLng
            if (viewModel.getMyLatLon() != nullLatLon)
                binding.btnSave.text = getString(R.string.update)

            binding.btnSave.setOnClickListener {
                viewModel.saveMyLatLon(latLng)
                navController.popBackStack()
                Snackbar.make(requireView(), "Saved :)", Snackbar.LENGTH_SHORT).show()
            }
        } else {
            val geocoder =
                Geocoder(requireContext().applicationContext, Locale(viewModel.getLang()))
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
                                    locationName = address[0].countryName + address[0].adminArea,
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