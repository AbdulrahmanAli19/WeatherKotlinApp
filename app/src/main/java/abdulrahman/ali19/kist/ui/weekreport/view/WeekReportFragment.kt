package abdulrahman.ali19.kist.ui.weekreport.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import abdulrahman.ali19.kist.databinding.WeekReportFragmentBinding
import abdulrahman.ali19.kist.data.pojo.model.ReportModel
import abdulrahman.ali19.kist.data.pojo.model.weather.Daily
import abdulrahman.ali19.kist.data.pojo.model.weather.Hourly

class WeekReportFragment : Fragment() {

    private lateinit var binding: WeekReportFragmentBinding
    private lateinit var navController: NavController
    private val args: WeekReportFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()

        binding.data = ReportModel(
            week = args.data.daily as ArrayList<Daily>,
            day = args.data.hourly as ArrayList<Hourly>
        )
        binding.executePendingBindings()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WeekReportFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

}