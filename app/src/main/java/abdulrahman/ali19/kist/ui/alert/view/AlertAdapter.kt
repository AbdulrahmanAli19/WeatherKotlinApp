package abdulrahman.ali19.kist.ui.alert.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import abdulrahman.ali19.kist.databinding.AlertLayoutBinding
import abdulrahman.ali19.kist.pojo.model.dbentities.AlertEntity


class AlertAdapter(
    private val list: ArrayList<AlertEntity> = arrayListOf(),
    private val listener : AlertAdapterListener
) :
    RecyclerView.Adapter<AlertAdapter.AlertViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun setList(newList: ArrayList<AlertEntity>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlertViewHolder =
        AlertViewHolder(AlertLayoutBinding.inflate(LayoutInflater.from(parent.context)), listener)

    override fun onBindViewHolder(holder: AlertViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int = if (list.isNullOrEmpty()) 0 else list.size
    inner class AlertViewHolder(val binding: AlertLayoutBinding, listener: AlertAdapterListener) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.image.setOnClickListener { listener.onDeleteImageClick(adapterPosition) }
        }
        fun bind(alertEntity: AlertEntity) {
            binding.data = alertEntity
            binding.executePendingBindings()
        }
    }

    interface AlertAdapterListener {
        fun onDeleteImageClick(pos: Int)
    }

}