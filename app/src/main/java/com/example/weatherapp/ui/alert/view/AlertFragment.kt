package com.example.weatherapp.ui.alert.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.viewModels
import com.example.weatherapp.R
import com.example.weatherapp.data.local.ConcreteLocalSource
import com.example.weatherapp.data.preferences.PreferenceProvider
import com.example.weatherapp.data.remote.ConnectionProvider
import com.example.weatherapp.data.remote.Resource
import com.example.weatherapp.data.remote.Status
import com.example.weatherapp.databinding.AlertFragmentBinding
import com.example.weatherapp.pojo.model.AlertModel
import com.example.weatherapp.pojo.model.dbentities.AlertEntity
import com.example.weatherapp.pojo.repo.Repository
import com.example.weatherapp.ui.alert.viewmodel.AlertViewModel
import com.example.weatherapp.ui.alert.viewmodel.AlertViewModelFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "AlertFragment"

class AlertFragment : Fragment() {

    private lateinit var binding: AlertFragmentBinding
    private var startDate: Long = 0
    private var endDate: Long = 0

    private val viewModel by viewModels<AlertViewModel> {
        AlertViewModelFactory(
            Repository.getInstance(
                remoteSource = ConnectionProvider,
                localSource = ConcreteLocalSource.getInstance(requireContext()),
                preferences = PreferenceProvider(requireContext())
            )
        )
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllAlerts().observe(viewLifecycleOwner) {
            setupLayout(it)
        }
        binding.btnAdd.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.alert_dialog)
            val btnSave = dialog.findViewById<Button>(R.id.btnSave)
            val btnCancel = dialog.findViewById<Button>(R.id.btnCancel)
            val txtStatDate = dialog.findViewById<TextView>(R.id.txtStartDate)
            val txtStatTime = dialog.findViewById<TextView>(R.id.txtStartTime)
            val txtEndDate = dialog.findViewById<TextView>(R.id.txtEndDate)
            val txtEndTime = dialog.findViewById<TextView>(R.id.txtEndTime)
            val cardStart = dialog.findViewById<CardView>(R.id.cardStart)
            val cardEnd = dialog.findViewById<CardView>(R.id.cardEnd)

            cardEnd.setOnClickListener {
                val endDate = Calendar.getInstance()
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                        endDate.set(Calendar.HOUR_OF_DAY, hour)
                        endDate.set(Calendar.MINUTE, minute)
                        txtEndTime.text = SimpleDateFormat("hh:mm aaa").format(endDate.time)
                        this.endDate = endDate.timeInMillis
                    }

                val dateSetListener =
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        endDate.set(Calendar.YEAR, year)
                        endDate.set(Calendar.MONTH, monthOfYear)
                        endDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        txtEndDate.text = SimpleDateFormat("dd MMM").format(endDate.time)
                        TimePickerDialog(
                            context,
                            timeSetListener,
                            endDate.get(Calendar.HOUR_OF_DAY),
                            endDate.get(Calendar.MINUTE),
                            false
                        ).show()
                    }

                DatePickerDialog(
                    requireContext(), dateSetListener,
                    endDate.get(Calendar.YEAR),
                    endDate.get(Calendar.MONTH),
                    endDate.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            cardStart.setOnClickListener {
                val startDate = Calendar.getInstance()
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                        startDate.set(Calendar.HOUR_OF_DAY, hour)
                        startDate.set(Calendar.MINUTE, minute)
                        txtStatTime.text = SimpleDateFormat("hh:mm aaa").format(startDate.time)
                        this.startDate = startDate.timeInMillis
                    }

                val dateSetListener =
                    DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                        startDate.set(Calendar.YEAR, year)
                        startDate.set(Calendar.MONTH, monthOfYear)
                        startDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        txtStatDate.text = SimpleDateFormat("dd MMM").format(startDate.time)
                        TimePickerDialog(
                            context,
                            timeSetListener,
                            startDate.get(Calendar.HOUR_OF_DAY),
                            startDate.get(Calendar.MINUTE),
                            false
                        ).show()
                    }

                DatePickerDialog(
                    requireContext(), dateSetListener,
                    startDate.get(Calendar.YEAR),
                    startDate.get(Calendar.MONTH),
                    startDate.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
            btnCancel.setOnClickListener { dialog.dismiss() }
            btnSave.setOnClickListener {
                viewModel.insertAlert(
                    AlertEntity(startDate = startDate, endDate = endDate)
                )
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun setupLayout(it: Resource<List<AlertEntity>>?) {
        if (it != null)
            when (it.status) {
                Status.LOADING -> {
                    Log.d(TAG, "setupLayout: called")
                    binding.progressBar.visibility = View.VISIBLE
                }
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    if (it.data.isNullOrEmpty()) {
                        binding.emptyLayout.visibility = View.VISIBLE
                    } else {
                        binding.data = AlertModel(it.data as ArrayList<AlertEntity>)
                        binding.executePendingBindings()
                    }
                }
                Status.ERROR -> {
                    Log.d(TAG, "setupLayout: ")
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AlertFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }


}