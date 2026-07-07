package com.example.alya_love.pertemuan_10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alya_love.R
import com.example.alya_love.room.LogistikEntity

class LogistikAdapter(
    private val dataList: MutableList<LogistikEntity>,
    private val onItemClick: (LogistikEntity) -> Unit,
    private val onEditClick: (LogistikEntity) -> Unit,
    private val onDeleteClick: (LogistikEntity) -> Unit
) : RecyclerView.Adapter<LogistikAdapter.LogistikViewHolder>() {

    inner class LogistikViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNamaBarang: TextView = view.findViewById(R.id.tvNamaBarang)
        val tvSatuan: TextView = view.findViewById(R.id.tvSatuan)
        val tvStok: TextView = view.findViewById(R.id.tvStok)
        val tvSumber: TextView = view.findViewById(R.id.tvSumber)
        val tvKejadian: TextView = view.findViewById(R.id.tvKejadian)
        val btnLihat: Button = view.findViewById(R.id.btnLihat)
        val btnEdit: Button = view.findViewById(R.id.btnEdit)
        val btnHapus: Button = view.findViewById(R.id.btnHapus)
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

        holder.btnLihat.setOnClickListener {
            onItemClick(item)
        }

        holder.btnEdit.setOnClickListener {
            onEditClick(item)
        }

        holder.btnHapus.setOnClickListener {
            onDeleteClick(item)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}