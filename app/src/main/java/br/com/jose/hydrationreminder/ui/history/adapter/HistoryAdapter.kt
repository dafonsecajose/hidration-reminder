package br.com.jose.hydrationreminder.ui.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.jose.hydrationreminder.data.model.HistoryDrink
import br.com.jose.hydrationreminder.databinding.HistoryItemBinding

class HistoryAdapter: ListAdapter<HistoryDrink, HistoryAdapter.ViewHolder>(DiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryAdapter.ViewHolder {
        return ViewHolder(HistoryItemBinding.inflate(LayoutInflater.from(parent.context),
            parent, false))
    }

    override fun onBindViewHolder(holder: HistoryAdapter.ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: HistoryItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HistoryDrink) {
            binding.tvQuantityHistory.text = "${item.amount.toInt()} ml"
            binding.tvTimeHistory.text = "${item.hour}"
        }
    }
}

class DiffCallback: DiffUtil.ItemCallback<HistoryDrink>() {
    override fun areItemsTheSame(oldItem: HistoryDrink, newItem: HistoryDrink) = oldItem == newItem

    override fun areContentsTheSame(oldItem: HistoryDrink, newItem: HistoryDrink): Boolean {
        val isAmountTheSame = oldItem.amount == newItem.amount
        val isDateTheSame = oldItem.date == newItem.date
        val isHourTheSame = oldItem.hour == newItem.hour
        val isIdTheSame = oldItem.id == newItem.id
       return isAmountTheSame && isDateTheSame && isHourTheSame && isIdTheSame
    }

}
