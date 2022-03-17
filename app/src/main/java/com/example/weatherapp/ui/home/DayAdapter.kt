package com.example.weatherapp.ui.home

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.DayItemBinding
import com.example.weatherapp.pojo.model.DayItem
import com.example.weatherapp.pojo.model.weather.Hourly
import com.example.weatherapp.util.getTime

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

    override fun getItemCount(): Int = if (!data.isEmpty()) data.size else data.size

    inner class DayViewHolder(private var _binding: DayItemBinding) :
        RecyclerView.ViewHolder(_binding.root) {
        fun bind(weather: Hourly) {
            _binding.day =
                DayItem(weather.weather[0].icon, weather.temp.toInt(), getTime(weather.dt))
            _binding.executePendingBindings()
        }
    }
}