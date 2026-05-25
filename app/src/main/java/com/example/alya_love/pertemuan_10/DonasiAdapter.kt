package com.example.alya_love.pertemuan_10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alya_love.R

class DonasiAdapter(
    private val dataList: List<Donasi>,
    private val onItemClick: (Donasi) -> Unit,
    private val onEditClick: (Donasi) -> Unit,
    private val onDeleteClick: (Donasi) -> Unit
) : RecyclerView.Adapter<DonasiAdapter.DonasiViewHolder>() {

    inner class DonasiViewHolder(val binding: View) : RecyclerView.ViewHolder(binding) {
        val ivGambar: ImageView = binding.findViewById(R.id.ivGambar)
        val tvNamaDonatur: TextView = binding.findViewById(R.id.tvNamaDonatur)
        val tvJenisDonasi: TextView = binding.findViewById(R.id.tvJenisDonasi)
        val tvNilai: TextView = binding.findViewById(R.id.tvNilai)
        val tvKejadian: TextView = binding.findViewById(R.id.tvKejadian)
        val btnLihat: Button = binding.findViewById(R.id.btnLihat)
        val btnEdit: Button = binding.findViewById(R.id.btnEdit)
        val btnHapus: Button = binding.findViewById(R.id.btnHapus)
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

        Glide.with(holder.itemView.context)
            .load(item.gambar)
            .into(holder.ivGambar)

        holder.btnLihat.setOnClickListener { onItemClick(item) }
        holder.btnEdit.setOnClickListener { onEditClick(item) }
        holder.btnHapus.setOnClickListener { onDeleteClick(item) }
    }

    override fun getItemCount(): Int = dataList.size
}