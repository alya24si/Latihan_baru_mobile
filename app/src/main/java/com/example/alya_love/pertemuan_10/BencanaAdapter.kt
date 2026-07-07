package com.example.alya_love.pertemuan_10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import androidx.recyclerview.widget.RecyclerView
import com.example.alya_love.R
import com.example.alya_love.room.BencanaEntity
import java.io.File

class BencanaAdapter(
    private val dataList: MutableList<BencanaEntity>,
    private val onItemClick: (BencanaEntity) -> Unit,
    private val onEditClick: (BencanaEntity) -> Unit,
    private val onDeleteClick: (BencanaEntity) -> Unit
) : RecyclerView.Adapter<BencanaAdapter.BencanaViewHolder>() {

    inner class BencanaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivGambar: ImageView = view.findViewById(R.id.ivGambar)
        val tvJudul: TextView = view.findViewById(R.id.tvJudul)
        val tvDeskripsi: TextView = view.findViewById(R.id.tvDeskripsi)
        val tvLokasi: TextView = view.findViewById(R.id.tvLokasi)
        val tvTanggal: TextView = view.findViewById(R.id.tvTanggal)
        val btnLihat: MaterialButton = view.findViewById(R.id.btnLihat)
        val btnEdit: MaterialButton = view.findViewById(R.id.btnEdit)
        val btnHapus: MaterialButton = view.findViewById(R.id.btnHapus)
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
        holder.tvLokasi.text = "📍 ${item.lokasi}"
        holder.tvTanggal.text = "📅 ${item.tanggal}"

        // ✅ LOAD GAMBAR: Cek URL atau file lokal
        if (item.gambar.isNotEmpty()) {
            if (item.gambar.startsWith("http://") || item.gambar.startsWith("https://")) {
                // URL internet
                Glide.with(holder.itemView.context)
                    .load(item.gambar)
                    .into(holder.ivGambar)
            } else {
                // File lokal
                val file = File(item.gambar)
                if (file.exists()) {
                    Glide.with(holder.itemView.context)
                        .load(file)
                        .into(holder.ivGambar)
                } else {
                    holder.ivGambar.setImageResource(android.R.drawable.ic_menu_gallery)
                }
            }
        } else {
            holder.ivGambar.setImageResource(android.R.drawable.ic_menu_gallery)
        }

        holder.btnLihat.setOnClickListener { onItemClick(item) }
        holder.btnEdit.setOnClickListener { onEditClick(item) }
        holder.btnHapus.setOnClickListener { onDeleteClick(item) }
    }

    override fun getItemCount(): Int = dataList.size
}