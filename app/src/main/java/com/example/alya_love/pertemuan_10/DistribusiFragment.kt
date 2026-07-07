package com.example.alya_love.pertemuan_10

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alya_love.databinding.FragmentDistribusiBinding
import com.example.alya_love.room.AppDatabase
import com.example.alya_love.room.DistribusiEntity
import kotlinx.coroutines.launch

class DistribusiFragment : Fragment() {

    private var _binding: FragmentDistribusiBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: AppDatabase
    private lateinit var adapter: DistribusiAdapter

    private val dataList = mutableListOf<DistribusiEntity>()

    private val editLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            loadData()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDistribusiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getDatabase(requireContext())

        binding.fabAdd.setOnClickListener {
            startActivity(
                Intent(requireContext(), DistribusiFormActivity::class.java)
            )
        }

        adapter = DistribusiAdapter(
            dataList,
            onItemClick = { distribusi ->
                val intent = Intent(requireContext(), DetailDistribusiActivity::class.java)
                intent.putExtra("EXTRA_ID", distribusi.id)
                startActivity(intent)
            },
            onEditClick = { distribusi ->
                val intent = Intent(requireContext(), DistribusiFormActivity::class.java)
                intent.putExtra("EXTRA_ID", distribusi.id)
                editLauncher.launch(intent)
            },
            onDeleteClick = { distribusi ->
                showDeleteConfirmation(distribusi)
            }
        )

        binding.rvDistribusi.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDistribusi.adapter = adapter

        insertDummyData()
    }

    private fun showDeleteConfirmation(distribusi: DistribusiEntity) {
        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Distribusi?")
            .setMessage("Apakah Anda yakin ingin menghapus distribusi \"${distribusi.namaBarang}\"?")
            .setPositiveButton("Hapus") { _, _ ->
                viewLifecycleOwner.lifecycleScope.launch {
                    db.distribusiDao().delete(distribusi)
                    Toast.makeText(requireContext(), "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                    loadData()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun insertDummyData() {
        viewLifecycleOwner.lifecycleScope.launch {
            if (db.distribusiDao().getAll().isEmpty()) {
                db.distribusiDao().insert(
                    DistribusiEntity(
                        namaBarang = "Paket Sembako",
                        jumlah = "500 Paket",
                        tujuan = "Desa Sukamaju",
                        tanggal = "2026-01-10",
                        status = "Tersalurkan"
                    )
                )
                db.distribusiDao().insert(
                    DistribusiEntity(
                        namaBarang = "Obat-obatan",
                        jumlah = "200 Box",
                        tujuan = "Rumah Sakit Umum",
                        tanggal = "2026-01-11",
                        status = "Dalam Proses"
                    )
                )
                db.distribusiDao().insert(
                    DistribusiEntity(
                        namaBarang = "Selimut",
                        jumlah = "300 Pcs",
                        tujuan = "Pengungsian A",
                        tanggal = "2026-01-12",
                        status = "Tersalurkan"
                    )
                )
                db.distribusiDao().insert(
                    DistribusiEntity(
                        namaBarang = "Makanan Siap Saji",
                        jumlah = "1000 Kotak",
                        tujuan = "Posko Bencana B",
                        tanggal = "2026-01-13",
                        status = "Dalam Proses"
                    )
                )
                db.distribusiDao().insert(
                    DistribusiEntity(
                        namaBarang = "Air Minum",
                        jumlah = "2000 Liter",
                        tujuan = "Desa Terisolir",
                        tanggal = "2026-01-14",
                        status = "Tersalurkan"
                    )
                )
            }
            loadData()
        }
    }

    private fun loadData() {
        viewLifecycleOwner.lifecycleScope.launch {
            val data = db.distribusiDao().getAll()
            dataList.clear()
            dataList.addAll(data)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}