package com.example.alya_love.pertemuan_10

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alya_love.R
import com.example.alya_love.room.BencanaEntity

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
        val btnLihat: Button = view.findViewById(R.id.btnLihat)
        val btnEdit: Button = view.findViewById(R.id.btnEdit)
        val btnHapus: Button = view.findViewById(R.id.btnHapus)
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

        if (item.gambar.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(Uri.parse(item.gambar))
                .into(holder.ivGambar)
        } else {
            holder.ivGambar.setImageResource(
                android.R.drawable.ic_menu_gallery
            )
        }

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