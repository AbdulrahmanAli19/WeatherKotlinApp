package com.example.weatherapp.ui.alert.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.data.local.ConcreteLocalSource
import com.example.weatherapp.data.preferences.PreferenceProvider
import com.example.weatherapp.data.remote.ConnectionProvider
import com.example.weatherapp.pojo.repo.Repository
import com.example.weatherapp.ui.alert.viewmodel.AlertViewModel
import com.example.weatherapp.ui.alert.viewmodel.AlertViewModelFactory

class AlertFragment : Fragment() {

    private val viewModel by viewModels<AlertViewModel> {
        AlertViewModelFactory(
            Repository.getInstance(
                remoteSource = ConnectionProvider,
                localSource = ConcreteLocalSource.getInstance(requireContext()),
                preferences = PreferenceProvider(requireContext())
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.alert_fragment, container, false)
    }


}