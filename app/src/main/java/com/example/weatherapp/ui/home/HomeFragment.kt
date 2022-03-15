package com.example.weatherapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.weatherapp.data.remote.Status
import com.example.weatherapp.databinding.FragmentHomeBinding
import com.example.weatherapp.ui.home.viewmodel.HomeViewModel
import com.example.weatherapp.ui.home.viewmodel.HomeViewModelFactory
import kotlin.math.log

class HomeFragment : Fragment() {
    private val TAG = "HomeFragment"
    private var _binding: FragmentHomeBinding? = null
    private lateinit var navController: NavController
    private val homeViewModel by viewModels<HomeViewModel> { HomeViewModelFactory(requireContext()) }

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        homeViewModel.getData().observe(viewLifecycleOwner) {
            it?.let {
                when (it.status) {
                    Status.SUCCESS -> Toast.makeText(
                        context,
                        "done ${it.data?.current?.temp}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Status.ERROR -> Log.d(TAG, "onViewCreated: ${it.message}")
                    Status.LOADING -> Log.d(TAG, "onViewCreated: loading")
                }
            }
        }
        binding.btnViewFullReport.setOnClickListener {
            navController.navigate(HomeFragmentDirections.actionNavHomeToWeekReportFragment())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}