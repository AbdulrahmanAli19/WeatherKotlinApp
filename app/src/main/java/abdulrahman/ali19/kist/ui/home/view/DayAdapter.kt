package abdulrahman.ali19.kist.ui.home.view

import abdulrahman.ali19.kist.data.pojo.model.DayItem
import abdulrahman.ali19.kist.data.pojo.model.weather.Hourly
import abdulrahman.ali19.kist.databinding.DayItemBinding
import abdulrahman.ali19.kist.util.getTime
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class DayAdapter(private val context: Context) : RecyclerView.Adapter<DayAdapter.DayViewHolder>() {

    private var data: ArrayList<Hourly> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(newData: ArrayList<Hourly>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayViewHolder =
        DayViewHolder(DayItemBinding.inflate(LayoutInflater.from(context)))

    override fun onBindViewHolder(holder: DayViewHolder, position: Int) =
        holder.bind(data[position])

    override fun getItemCount(): Int = if (data.isNotEmpty()) data.size else data.size

    inner class DayViewHolder(private var _binding: DayItemBinding) :
        RecyclerView.ViewHolder(_binding.root) {
        fun bind(weather: Hourly) {
            _binding.day =
                DayItem(
                    weather.weather[0].icon,
                    weather.temp.toInt(),
                    getTime(weather.dt)
                )
            _binding.executePendingBindings()
        }
    }
}