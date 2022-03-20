package com.example.weatherapp.ui.settings.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherapp.data.local.ConcreteLocalSource
import com.example.weatherapp.data.preferences.AppUnits
import com.example.weatherapp.data.preferences.PreferenceProvider
import com.example.weatherapp.data.remote.ConnectionProvider
import com.example.weatherapp.databinding.FragmentSettingsBinding
import com.example.weatherapp.pojo.repo.Repository
import com.example.weatherapp.ui.settings.viewmodel.SettingsViewModel
import com.example.weatherapp.ui.settings.viewmodel.SettingsViewModelFactory

private const val TAG = "SettingsFragment.dev"

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    private val viewModel by viewModels<SettingsViewModel> {
        SettingsViewModelFactory(
            Repository.getInstance(
                remoteSource = ConnectionProvider,
                localSource = ConcreteLocalSource.getInstance(requireContext()),
                preferences = PreferenceProvider(requireContext())
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpLayout()

    }

    private fun setUpLayout() {
        when (viewModel.getLanguage()) {
            AppUnits.AR.string -> binding.langArabic.isChecked = true
            AppUnits.EN.string -> binding.langEnd.isChecked = true
        }

        when (viewModel.getWindSpeedUnit()) {
            AppUnits.METER_BY_SECOND.string -> binding.mByS.isChecked = true
            AppUnits.MILE_BY_HOUR.string -> binding.mByH.isChecked = true
        }

        when (viewModel.getTempUnit()) {
            AppUnits.FAHRENHEIT.string -> binding.tempFahrenheit.isChecked = true
            AppUnits.KELVIN.string -> binding.tempKelvin.isChecked = true
            AppUnits.CELSIUS.string -> binding.tempCelsius.isChecked = true
        }

        binding.radioLang.setOnCheckedChangeListener { _, _id ->
            when (_id) {
                binding.langEnd.id -> {
                    viewModel.setLanguage(AppUnits.EN.toString())
                    Toast.makeText(requireContext(), binding.langEnd.text, Toast.LENGTH_SHORT)
                        .show()
                }
                binding.langArabic.id -> {
                    viewModel.setLanguage(AppUnits.AR.string)
                    Toast.makeText(requireContext(), binding.langArabic.text, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        binding.radioTemp.setOnCheckedChangeListener { _, id ->
            when (id) {
                binding.tempFahrenheit.id -> {
                    viewModel.setTempUnit(AppUnits.FAHRENHEIT.string)
                    Toast.makeText(
                        requireContext(),
                        binding.tempFahrenheit.text,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
                binding.tempCelsius.id -> {
                    viewModel.setTempUnit(AppUnits.CELSIUS.string)
                    Toast.makeText(requireContext(), binding.tempCelsius.text, Toast.LENGTH_SHORT)
                        .show()
                }
                binding.tempKelvin.id -> {
                    viewModel.setTempUnit(AppUnits.KELVIN.string)
                    Toast.makeText(requireContext(), binding.tempKelvin.text, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
        binding.radioWind.setOnCheckedChangeListener { _, id ->
            when (id) {
                binding.mByH.id -> {
                    viewModel.setWindSpeedUnit(AppUnits.MILE_BY_HOUR.string)
                    Toast.makeText(
                        requireContext(),
                        binding.mByH.text,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                binding.mByS.id -> {
                    viewModel.setWindSpeedUnit(AppUnits.METER_BY_SECOND.string)
                    Toast.makeText(requireContext(), binding.mByS.text, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }


}