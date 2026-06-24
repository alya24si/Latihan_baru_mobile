package com.example.alya_love.pertemuan_10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.button.MaterialButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.alya_love.R
import com.example.alya_love.room.PoskoEntity

class PoskoAdapter(
    private val dataList: MutableList<PoskoEntity>,
    private val onItemClick: (PoskoEntity) -> Unit,  // ← TAMBAHKAN
    private val onEditClick: (PoskoEntity) -> Unit,  // ← TAMBAHKAN
    private val onDeleteClick: (PoskoEntity) -> Unit
) : RecyclerView.Adapter<PoskoAdapter.PoskoViewHolder>() {

    inner class PoskoViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val ivGambar: ImageView = view.findViewById(R.id.ivGambar)
        val tvNamaPosko: TextView = view.findViewById(R.id.tvNamaPosko)
        val tvAlamat: TextView = view.findViewById(R.id.tvAlamat)
        val tvKapasitas: TextView = view.findViewById(R.id.tvKapasitas)
        val tvPenanggungJawab: TextView = view.findViewById(R.id.tvPenanggungJawab)
        val tvTelepon: TextView = view.findViewById(R.id.tvTelepon)
        val btnLihat: MaterialButton = view.findViewById(R.id.btnLihat)  // ← TAMBAHKAN
        val btnEdit: MaterialButton = view.findViewById(R.id.btnEdit)    // ← TAMBAHKAN
        val btnHapus: MaterialButton = view.findViewById(R.id.btnHapus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoskoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_posko, parent, false)
        return PoskoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PoskoViewHolder, position: Int) {
        val item = dataList[position]

        holder.tvNamaPosko.text = item.namaPosko
        holder.tvAlamat.text = "📍 ${item.alamat}"
        holder.tvKapasitas.text = "👥 Kapasitas: ${item.kapasitas}"
        holder.tvPenanggungJawab.text = "👤 PJ: ${item.penanggungJawab}"
        holder.tvTelepon.text = "📞 ${item.telepon}"

        if (item.gambar.isNotEmpty()) {
            Glide.with(holder.itemView.context)
                .load(item.gambar)
                .into(holder.ivGambar)
        }

        // ← TAMBAHKAN CLICK LISTENERS
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

    override fun getItemCount(): Int = dataList.size
}