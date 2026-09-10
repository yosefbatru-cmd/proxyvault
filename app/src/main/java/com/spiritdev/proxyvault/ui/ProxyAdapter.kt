package com.spiritdev.proxyvault.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.spiritdev.proxyvault.databinding.ItemProxyBinding
import com.spiritdev.proxyvault.model.ProxyItem

class ProxyAdapter(
    private val onCopy: (ProxyItem) -> Unit,
    private val onTest: (ProxyItem) -> Unit
) : RecyclerView.Adapter<ProxyAdapter.VH>() {

    private val items = mutableListOf<ProxyItem>()

    fun submit(list: List<ProxyItem>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = ItemProxyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    inner class VH(private val binding: ItemProxyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ProxyItem) {
            binding.tvAddress.text = item.address
            binding.tvMeta.text = "${item.protocol.uppercase()} · ${item.displaySpeed} · ${item.source}"
            binding.tvCountry.text = item.countryCode
            binding.root.setOnClickListener { onCopy(item) }
            binding.root.setOnLongClickListener {
                onTest(item)
                true
            }
        }
    }
}
