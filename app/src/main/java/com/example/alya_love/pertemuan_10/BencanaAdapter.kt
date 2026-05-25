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

class BencanaAdapter(
    private val dataList: List<Bencana>,
    private val onItemClick: (Bencana) -> Unit,
    private val onEditClick: (Bencana) -> Unit,
    private val onDeleteClick: (Bencana) -> Unit
) : RecyclerView.Adapter<BencanaAdapter.BencanaViewHolder>() {

    inner class BencanaViewHolder(val binding: View) : RecyclerView.ViewHolder(binding) {
        val ivGambar: ImageView = binding.findViewById(R.id.ivGambar)
        val tvJudul: TextView = binding.findViewById(R.id.tvJudul)
        val tvDeskripsi: TextView = binding.findViewById(R.id.tvDeskripsi)
        val tvLokasi: TextView = binding.findViewById(R.id.tvLokasi)
        val tvTanggal: TextView = binding.findViewById(R.id.tvTanggal)
        val btnLihat: Button = binding.findViewById(R.id.btnLihat)
        val btnEdit: Button = binding.findViewById(R.id.btnEdit)
        val btnHapus: Button = binding.findViewById(R.id.btnHapus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BencanaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_bencana, parent, false)
        return BencanaViewHolder(view)
    }

    override fun onBindViewHolder(holder: BencanaViewHolder, position: Int) {
        val item = dataList[position]

        holder.tvJudul.text = item.judul
        holder.tvDeskripsi.text = item.deskripsi
        holder.tvLokasi.text = "Lokasi: ${item.lokasi}"
        holder.tvTanggal.text = "Tanggal: ${item.tanggal}"

        Glide.with(holder.itemView.context)
            .load(item.gambar)
            .into(holder.ivGambar)

        holder.btnLihat.setOnClickListener { onItemClick(item) }
        holder.btnEdit.setOnClickListener { onEditClick(item) }
        holder.btnHapus.setOnClickListener { onDeleteClick(item) }
    }

    override fun getItemCount(): Int = dataList.size
}