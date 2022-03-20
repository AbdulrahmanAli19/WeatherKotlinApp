package com.example.weatherapp.ui.settings.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.weatherapp.databinding.FragmentSettingsBinding
import com.example.weatherapp.ui.settings.viewmodel.SettingsViewModel

private const val TAG = "SettingsFragment.dev"

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)

        binding = FragmentSettingsBinding.inflate(inflater, container, false)

        return binding.root
    }


}