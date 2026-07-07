package com.example.alya_love.pertemuan_10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alya_love.R
import com.example.alya_love.room.DonasiEntity

class DonasiAdapter(
    private val dataList: MutableList<DonasiEntity>,
    private val onItemClick: (DonasiEntity) -> Unit,
    private val onEditClick: (DonasiEntity) -> Unit,
    private val onDeleteClick: (DonasiEntity) -> Unit
) : RecyclerView.Adapter<DonasiAdapter.DonasiViewHolder>() {

    inner class DonasiViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNamaDonatur: TextView = view.findViewById(R.id.tvNamaDonatur)
        val tvJenisDonasi: TextView = view.findViewById(R.id.tvJenisDonasi)
        val tvNilai: TextView = view.findViewById(R.id.tvNilai)
        val tvKejadian: TextView = view.findViewById(R.id.tvKejadian)
        val btnLihat: Button = view.findViewById(R.id.btnLihat)
        val btnEdit: Button = view.findViewById(R.id.btnEdit)
        val btnHapus: Button = view.findViewById(R.id.btnHapus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonasiViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_donasi, parent, false)
        return DonasiViewHolder(view)
    }

    override fun onBindViewHolder(holder: DonasiViewHolder, position: Int) {
        val item = dataList[position]

        holder.tvNamaDonatur.text = item.namaDonatur
        holder.tvJenisDonasi.text = "Jenis Donasi: ${item.jenisDonasi}"
        holder.tvNilai.text = "Nilai: ${item.nilai}"
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