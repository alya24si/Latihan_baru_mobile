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

class PoskoAdapter(
    private val dataList: List<Posko>,
    private val onItemClick: (Posko) -> Unit,
    private val onEditClick: (Posko) -> Unit,
    private val onDeleteClick: (Posko) -> Unit
) : RecyclerView.Adapter<PoskoAdapter.PoskoViewHolder>() {

    inner class PoskoViewHolder(val binding: View) : RecyclerView.ViewHolder(binding) {
        val ivGambar: ImageView = binding.findViewById(R.id.ivGambar)
        val tvNamaOrganisasi: TextView = binding.findViewById(R.id.tvNamaOrganisasi)
        val tvAlamat: TextView = binding.findViewById(R.id.tvAlamat)
        val tvKontak: TextView = binding.findViewById(R.id.tvKontak)
        val tvPenanggungJawab: TextView = binding.findViewById(R.id.tvPenanggungJawab)
        val btnLihat: Button = binding.findViewById(R.id.btnLihat)
        val btnEdit: Button = binding.findViewById(R.id.btnEdit)
        val btnHapus: Button = binding.findViewById(R.id.btnHapus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoskoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_posko, parent, false)
        return PoskoViewHolder(view)
    }

    override fun onBindViewHolder(holder: PoskoViewHolder, position: Int) {
        val item = dataList[position]

        holder.tvNamaOrganisasi.text = item.namaOrganisasi
        holder.tvAlamat.text = item.alamat
        holder.tvKontak.text = item.kontak
        holder.tvPenanggungJawab.text = item.penanggungJawab

        Glide.with(holder.itemView.context)
            .load(item.gambar)
            .into(holder.ivGambar)

        holder.btnLihat.setOnClickListener { onItemClick(item) }
        holder.btnEdit.setOnClickListener { onEditClick(item) }
        holder.btnHapus.setOnClickListener { onDeleteClick(item) }
    }

    override fun getItemCount(): Int = dataList.size
}