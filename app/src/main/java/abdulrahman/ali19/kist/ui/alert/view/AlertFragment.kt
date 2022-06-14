package abdulrahman.ali19.kist.ui.alert.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import abdulrahman.ali19.kist.R
import abdulrahman.ali19.kist.data.local.ConcreteLocalSource
import abdulrahman.ali19.kist.data.preferences.PreferenceProvider
import abdulrahman.ali19.kist.data.remote.ConnectionProvider
import abdulrahman.ali19.kist.data.remote.Resource
import abdulrahman.ali19.kist.data.remote.Status
import abdulrahman.ali19.kist.databinding.AlertFragmentBinding
import abdulrahman.ali19.kist.pojo.model.AlertModel
import abdulrahman.ali19.kist.pojo.model.dbentities.AlertEntity
import abdulrahman.ali19.kist.pojo.repo.Repository
import abdulrahman.ali19.kist.ui.alert.viewmodel.AlertViewModel
import abdulrahman.ali19.kist.ui.alert.viewmodel.AlertViewModelFactory
import abdulrahman.ali19.kist.worker.AddAlertRemainder
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

private const val TAG = "AlertFragment"

class AlertFragment : Fragment(), AlertAdapter.AlertAdapterListener {

    private lateinit var list: ArrayList<AlertEntity>
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

                val dateSetListener =
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
                        endDate.set(Calendar.YEAR, year)
                        endDate.set(Calendar.MONTH, monthOfYear)
                        endDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                        txtEndDate.text = SimpleDateFormat("dd MMM").format(endDate.time)
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
                    TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                        startDate.set(Calendar.HOUR_OF_DAY, hour)
                        startDate.set(Calendar.MINUTE, minute)
                        txtStatTime.text = SimpleDateFormat("hh:mm aaa").format(startDate.time)
                        this.startDate = startDate.timeInMillis
                    }

                val dateSetListener =
                    DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
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
                val pojo = AlertEntity(startDate = startDate, endDate = endDate)
                viewModel.insertAlert(
                    pojo
                )
                binding.emptyLayout.visibility = View.GONE
                viewModel.getAllAlerts().observe(viewLifecycleOwner) { setupLayout(it) }
                AddAlertRemainder.addPeriodicAlert(
                    delay = startDate,
                    context = requireContext(),
                    workerTag = pojo.id.toString()
                )
                dialog.dismiss()
            }
            dialog.show()
        }
    }

    private fun setupLayout(it: Resource<List<AlertEntity>>?) {
        if (it != null) {
            if (!it.data.isNullOrEmpty())
                list = it.data as ArrayList<AlertEntity>
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
                        binding.data = AlertModel(it.data as ArrayList<AlertEntity>, this)
                        binding.executePendingBindings()
                    }
                }
                Status.ERROR -> {
                    Log.d(TAG, "setupLayout: ")
                }
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

    override fun onDeleteImageClick(pos: Int) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.delete_alert))
            .setMessage(getString(R.string.delete_alert_body))
            .setNegativeButton(getString(R.string.cancel)) { _, _ -> }
            .setPositiveButton(getString(R.string.delte)) { dialog, which ->
                viewModel.removeAlert(list[0])
                AddAlertRemainder.removeWorkers(
                    context = requireContext(),
                    workerTag = list[pos].id.toString()
                )
                list.removeAt(pos)
                binding.data = AlertModel(list, this)
                binding.executePendingBindings()
                if (list.isNullOrEmpty()) {
                    binding.emptyLayout.visibility = View.VISIBLE
                }
            }.show()
    }


}