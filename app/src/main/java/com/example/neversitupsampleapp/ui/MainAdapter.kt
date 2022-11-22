package com.example.neversitupsampleapp.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.neversitupsampleapp.R
import com.example.neversitupsampleapp.data.CurrentPriceDbData
import kotlinx.android.synthetic.main.item_currency.view.*

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val diffCallback = object : DiffUtil.ItemCallback<CurrentPriceDbData>() {
        override fun areItemsTheSame(
            oldItem: CurrentPriceDbData,
            newItem: CurrentPriceDbData
        ): Boolean {
            return oldItem.uid == newItem.uid
        }

        override fun areContentsTheSame(
            oldItem: CurrentPriceDbData,
            newItem: CurrentPriceDbData
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<CurrentPriceDbData>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(
                parent.context
            ).inflate(
                R.layout.item_currency,
                parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = differ.currentList[position]
        holder.itemView.apply {
            tv_time.text = item.time
            tv_usd_price.text = item.usdPrice
            tv_gbp_price.text = item.gbpPrice
            tv_eur_price.text = item.eurPrice
        }

    }
}