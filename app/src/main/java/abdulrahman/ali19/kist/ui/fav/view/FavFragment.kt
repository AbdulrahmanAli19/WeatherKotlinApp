package abdulrahman.ali19.kist.ui.fav.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import abdulrahman.ali19.kist.R
import abdulrahman.ali19.kist.data.local.ConcreteLocalSource
import abdulrahman.ali19.kist.data.preferences.PreferenceProvider
import abdulrahman.ali19.kist.data.remote.ConnectionProvider
import abdulrahman.ali19.kist.data.remote.Resource
import abdulrahman.ali19.kist.data.remote.Status
import abdulrahman.ali19.kist.databinding.FragmentFavBinding
import abdulrahman.ali19.kist.pojo.model.FavModel
import abdulrahman.ali19.kist.pojo.model.dbentities.FavoriteEntity
import abdulrahman.ali19.kist.pojo.repo.Repository
import abdulrahman.ali19.kist.ui.fav.viewmodel.FavViewModel
import abdulrahman.ali19.kist.ui.fav.viewmodel.FavViewModelFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.dialog.MaterialAlertDialogBuilder

private const val TAG = "FavFragment.dev"

class FavFragment : Fragment(), FavAdapter.FavAdapterInterface {

    private lateinit var binding: FragmentFavBinding
    private lateinit var navController: NavController
    private lateinit var cashedData: ArrayList<FavoriteEntity>
    private val viewModel by viewModels<FavViewModel> {
        FavViewModelFactory(
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
        binding.btnAdd.setOnClickListener {
            navController.navigate(FavFragmentDirections.actionNavFavToSelectLocationFragment(false))
        }
        viewModel.getFavoriteList().observe(viewLifecycleOwner) { setupLayout(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun setupLayout(response: Resource<List<FavoriteEntity>>) {
        when (response.status) {
            Status.LOADING -> {
                Log.d(TAG, "setupLayout: LOADING .....")
                binding.progressBar.visibility = View.VISIBLE
            }
            Status.SUCCESS -> {
                if (response.data != null && !response.data.isNullOrEmpty()) {
                    cashedData = response.data as ArrayList<FavoriteEntity>
                    binding.progressBar.visibility = View.GONE
                    binding.emptyLayout.visibility = View.GONE
                    binding.data = FavModel(this, response.data)
                    binding.executePendingBindings()
                } else {
                    Log.d(TAG, "setupLayout: DATA IS NULL ")
                    binding.emptyLayout.visibility = View.VISIBLE
                    binding.progressBar.visibility = View.GONE

                }
            }
            Status.ERROR -> {
                binding.progressBar.visibility = View.GONE
                binding.emptyLayout.visibility = View.VISIBLE
                Log.d(TAG, "setupLayout: error")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val act = requireActivity() as abdulrahman.ali19.kist.MainActivity
        act.setUpNavigationWithHamburger()
    }

    override fun onDeleteImageClick(pos: Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete_alert))
            .setMessage(getString(R.string.fav_delete_body))
            .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
            .setPositiveButton(getString(R.string.delte)) { dialog, which ->
                viewModel.deleteFromFavorite(cashedData[0])
                cashedData.removeAt(pos)
                binding.data = FavModel(this, cashedData)
                binding.executePendingBindings()
                if (cashedData.isNullOrEmpty()) {
                    binding.emptyLayout.visibility = View.VISIBLE
                }
            }.show()

    }

    override fun onItemClick(pos: Int) {
        val data = cashedData[pos].cashedData
        navController.navigate(
            FavFragmentDirections.actionNavFavToNavHome(
                data = data,
                latlog = LatLng(data.lat, data.lon)
            )
        )
        val act = requireActivity() as abdulrahman.ali19.kist.MainActivity
        act.setUpNavigationWithNoHamburger()
    }

}