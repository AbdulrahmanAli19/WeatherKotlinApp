package com.example.weatherapp.ui.weekreport

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.weatherapp.databinding.WeekReportFragmentBinding

class WeekReportFragment : Fragment() {

    val viewMode by viewModels<WeekReportViewModel>()
    lateinit var binding : WeekReportFragmentBinding
    private lateinit var viewModel: WeekReportViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WeekReportFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}