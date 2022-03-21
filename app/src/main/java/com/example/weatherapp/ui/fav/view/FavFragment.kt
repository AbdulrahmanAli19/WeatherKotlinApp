package com.example.weatherapp.ui.fav.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.MainActivity
import com.example.weatherapp.R
import com.example.weatherapp.data.local.ConcreteLocalSource
import com.example.weatherapp.data.preferences.PreferenceProvider
import com.example.weatherapp.data.remote.ConnectionProvider
import com.example.weatherapp.data.remote.Resource
import com.example.weatherapp.data.remote.Status
import com.example.weatherapp.databinding.FragmentFavBinding
import com.example.weatherapp.pojo.model.FavModel
import com.example.weatherapp.pojo.model.dbentities.FavoriteEntity
import com.example.weatherapp.pojo.repo.Repository
import com.example.weatherapp.ui.fav.viewmodel.FavViewModel
import com.example.weatherapp.ui.fav.viewmodel.FavViewModelFactory
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
        val act = requireActivity() as MainActivity
        act.setUpNavigationWithHamburger()
    }

    override fun onDeleteImageClick(pos: Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete alert")
            .setMessage("Are you sure you want to delete the place ?")
            .setNegativeButton("Cancel") { _, _ -> }
            .setPositiveButton("Delete") { dialog, which ->
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
        navController.navigate(FavFragmentDirections.actionNavFavToNavHome(data))
        val act = requireActivity() as MainActivity
        act.setUpNavigationWithNoHamburger()
    }

}