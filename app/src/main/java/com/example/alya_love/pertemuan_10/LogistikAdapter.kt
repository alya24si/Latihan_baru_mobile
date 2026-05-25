package com.example.alya_love.pertemuan_10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alya_love.R

class LogistikAdapter(
    private val dataList: List<Logistik>,
    private val onItemClick: (Logistik) -> Unit,
    private val onEditClick: (Logistik) -> Unit,
    private val onDeleteClick: (Logistik) -> Unit
) : RecyclerView.Adapter<LogistikAdapter.LogistikViewHolder>() {

    inner class LogistikViewHolder(val binding: View) : RecyclerView.ViewHolder(binding) {
        val tvNamaBarang: TextView = binding.findViewById(R.id.tvNamaBarang)
        val tvSatuan: TextView = binding.findViewById(R.id.tvSatuan)
        val tvStok: TextView = binding.findViewById(R.id.tvStok)
        val tvSumber: TextView = binding.findViewById(R.id.tvSumber)
        val tvKejadian: TextView = binding.findViewById(R.id.tvKejadian)
        val btnLihat: Button = binding.findViewById(R.id.btnLihat)
        val btnEdit: Button = binding.findViewById(R.id.btnEdit)
        val btnHapus: Button = binding.findViewById(R.id.btnHapus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LogistikViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_logistik, parent, false)
        return LogistikViewHolder(view)
    }

    override fun onBindViewHolder(holder: LogistikViewHolder, position: Int) {
        val item = dataList[position]

        holder.tvNamaBarang.text = item.namaBarang
        holder.tvSatuan.text = "Satuan: ${item.satuan}"
        holder.tvStok.text = item.stok.toString()
        holder.tvSumber.text = "Sumber: ${item.sumber}"
        holder.tvKejadian.text = "Kejadian: ${item.kejadian}"

        holder.btnLihat.setOnClickListener { onItemClick(item) }
        holder.btnEdit.setOnClickListener { onEditClick(item) }
        holder.btnHapus.setOnClickListener { onDeleteClick(item) }
    }

    override fun getItemCount(): Int = dataList.size
}