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
import com.example.alya_love.databinding.FragmentLogistikBinding
import com.example.alya_love.room.AppDatabase
import com.example.alya_love.room.LogistikEntity
import kotlinx.coroutines.launch

class LogistikFragment : Fragment() {

    private var _binding: FragmentLogistikBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: AppDatabase
    private lateinit var adapter: LogistikAdapter

    private val dataList = mutableListOf<LogistikEntity>()

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
        _binding = FragmentLogistikBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getDatabase(requireContext())

        binding.fabAdd.setOnClickListener {
            startActivity(
                Intent(requireContext(), LogistikFormActivity::class.java)
            )
        }

        adapter = LogistikAdapter(
            dataList,
            onItemClick = { logistik ->
                val intent = Intent(requireContext(), DetailLogistikActivity::class.java)
                intent.putExtra("EXTRA_ID", logistik.id)
                startActivity(intent)
            },
            onEditClick = { logistik ->
                val intent = Intent(requireContext(), LogistikFormActivity::class.java)
                intent.putExtra("EXTRA_ID", logistik.id)
                editLauncher.launch(intent)
            },
            onDeleteClick = { logistik ->
                showDeleteConfirmation(logistik)
            }
        )

        binding.rvLogistik.layoutManager = LinearLayoutManager(requireContext())
        binding.rvLogistik.adapter = adapter

        insertDummyData()
    }

    private fun showDeleteConfirmation(logistik: LogistikEntity) {
        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Logistik?")
            .setMessage("Apakah Anda yakin ingin menghapus logistik \"${logistik.namaBarang}\"?")
            .setPositiveButton("Hapus") { _, _ ->
                viewLifecycleOwner.lifecycleScope.launch {
                    db.logistikDao().delete(logistik)
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
            if (db.logistikDao().getAll().isEmpty()) {
                db.logistikDao().insert(
                    LogistikEntity(
                        namaBarang = "Beras",
                        satuan = "Karung",
                        stok = 500,
                        sumber = "Perum BULOG",
                        kejadian = "Banjir"
                    )
                )
                db.logistikDao().insert(
                    LogistikEntity(
                        namaBarang = "Obat-obatan",
                        satuan = "Box",
                        stok = 200,
                        sumber = "CV Mandasari",
                        kejadian = "Gempa Bumi"
                    )
                )
                db.logistikDao().insert(
                    LogistikEntity(
                        namaBarang = "Selimut",
                        satuan = "Pcs",
                        stok = 300,
                        sumber = "PD Lazuardi",
                        kejadian = "Angin Puting Beliung"
                    )
                )
                db.logistikDao().insert(
                    LogistikEntity(
                        namaBarang = "Makanan Siap Saji",
                        satuan = "Kotak",
                        stok = 1000,
                        sumber = "Yayasan Sudiati Wastuti",
                        kejadian = "Tanah Longsor"
                    )
                )
                db.logistikDao().insert(
                    LogistikEntity(
                        namaBarang = "Air Minum",
                        satuan = "Liter",
                        stok = 2000,
                        sumber = "PDAM",
                        kejadian = "Kekeringan"
                    )
                )
            }
            loadData()
        }
    }

    private fun loadData() {
        viewLifecycleOwner.lifecycleScope.launch {
            val data = db.logistikDao().getAll()
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