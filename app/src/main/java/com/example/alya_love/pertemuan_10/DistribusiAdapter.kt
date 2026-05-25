package com.example.alya_love.pertemuan_10

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alya_love.R

class DistribusiAdapter(
    private val dataList: List<Distribusi>,
    private val onItemClick: (Distribusi) -> Unit,
    private val onEditClick: (Distribusi) -> Unit,
    private val onDeleteClick: (Distribusi) -> Unit
) : RecyclerView.Adapter<DistribusiAdapter.DistribusiViewHolder>() {

    inner class DistribusiViewHolder(val binding: View) : RecyclerView.ViewHolder(binding) {
        val tvNamaBarang: TextView = binding.findViewById(R.id.tvNamaBarang)
        val tvJumlah: TextView = binding.findViewById(R.id.tvJumlah)
        val tvTujuan: TextView = binding.findViewById(R.id.tvTujuan)
        val tvTanggal: TextView = binding.findViewById(R.id.tvTanggal)
        val tvStatus: TextView = binding.findViewById(R.id.tvStatus)
        val btnLihat: Button = binding.findViewById(R.id.btnLihat)
        val btnEdit: Button = binding.findViewById(R.id.btnEdit)
        val btnHapus: Button = binding.findViewById(R.id.btnHapus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DistribusiViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_distribusi, parent, false)
        return DistribusiViewHolder(view)
    }

    override fun onBindViewHolder(holder: DistribusiViewHolder, position: Int) {
        val item = dataList[position]

        holder.tvNamaBarang.text = item.namaBarang
        holder.tvJumlah.text = "Jumlah: ${item.jumlah}"
        holder.tvTujuan.text = "Tujuan: ${item.tujuan}"
        holder.tvTanggal.text = "Tanggal: ${item.tanggal}"
        holder.tvStatus.text = "Status: ${item.status}"

        holder.btnLihat.setOnClickListener { onItemClick(item) }
        holder.btnEdit.setOnClickListener { onEditClick(item) }
        holder.btnHapus.setOnClickListener { onDeleteClick(item) }
    }

    override fun getItemCount(): Int = dataList.size
}