package com.example.weatherapp.ui.weekreport

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.databinding.WeekItemBinding
import com.example.weatherapp.pojo.model.WeekModel
import com.example.weatherapp.pojo.model.weather.Daily

class WeekAdapter() : RecyclerView.Adapter<WeekAdapter.WeekViewHolder>() {

    private val arrayList: ArrayList<Daily> = arrayListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setWeek(list: ArrayList<Daily>) {
        arrayList.clear()
        arrayList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeekViewHolder =
        WeekViewHolder(WeekItemBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: WeekViewHolder, position: Int) =
        holder.bind(arrayList[position])

    override fun getItemCount(): Int = if (arrayList.isNotEmpty()) arrayList.size else 0

    inner class WeekViewHolder(var binding: WeekItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(daily: Daily) {
            binding.data = WeekModel(
                daily.dt,
                daily.temp,
                daily.weather[0].icon
            )
            binding.executePendingBindings()
        }
    }

}