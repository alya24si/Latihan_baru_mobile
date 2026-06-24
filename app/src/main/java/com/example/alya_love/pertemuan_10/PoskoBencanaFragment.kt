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
import com.example.alya_love.databinding.FragmentPoskoBencanaBinding
import com.example.alya_love.room.AppDatabase
import com.example.alya_love.room.PoskoEntity
import kotlinx.coroutines.launch

class PoskoBencanaFragment : Fragment() {

    private var _binding: FragmentPoskoBencanaBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: AppDatabase
    private lateinit var adapter: PoskoAdapter
    private val dataList = mutableListOf<PoskoEntity>()

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
        _binding = FragmentPoskoBencanaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getDatabase(requireContext())

        binding.fabTambahPosko.setOnClickListener {
            startActivity(Intent(requireContext(), TambahPoskoActivity::class.java))
        }

        adapter = PoskoAdapter(
            dataList,
            onItemClick = { posko ->  // ← LIHAT DETAIL
                val intent = Intent(requireContext(), DetailPoskoActivity::class.java)
                intent.putExtra("EXTRA_ID", posko.id)
                startActivity(intent)
            },
            onEditClick = { posko ->  // ← EDIT
                val intent = Intent(requireContext(), EditPoskoActivity::class.java)
                intent.putExtra("EXTRA_ID", posko.id)
                editLauncher.launch(intent)
            },
            onDeleteClick = { posko ->  // ← HAPUS
                showDeleteConfirmation(posko)
            }
        )

        binding.rvPoskoBencana.layoutManager = LinearLayoutManager(requireContext())
        binding.rvPoskoBencana.adapter = adapter

        insertDummyData()
    }

    private fun showDeleteConfirmation(posko: PoskoEntity) {
        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Posko?")
            .setMessage("Apakah Anda yakin ingin menghapus \"${posko.namaPosko}\"?")
            .setPositiveButton("Hapus") { _, _ ->
                viewLifecycleOwner.lifecycleScope.launch {
                    db.poskoDao().delete(posko)
                    Toast.makeText(requireContext(), "Posko berhasil dihapus", Toast.LENGTH_SHORT).show()
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
            if (db.poskoDao().getAll().isEmpty()) {
                // 1. Posko Utama Pekanbaru
                db.poskoDao().insert(
                    PoskoEntity(
                        namaPosko = "Posko Utama Pekanbaru",
                        alamat = "Jl. Sudirman No. 123, Pekanbaru",
                        kapasitas = "500 Orang",
                        penanggungJawab = "Dr. Ahmad Wijaya",
                        telepon = "081234567890",
                        gambar = "https://images.unsplash.com/photo-1582139329536-e7284fece509?w=400&h=300&fit=crop"
                    )
                )

                // 2. Posko Evakuasi Siak
                db.poskoDao().insert(
                    PoskoEntity(
                        namaPosko = "Posko Evakuasi Siak",
                        alamat = "Jl. Hangtuah No. 45, Siak",
                        kapasitas = "300 Orang",
                        penanggungJawab = "Relawan Siaga",
                        telepon = "082345678901",
                        gambar = "https://images.unsplash.com/photo-1469571486292-0ba58a3f068b?w=400&h=300&fit=crop"
                    )
                )

                // 3. Posko Logistik Bengkalis
                db.poskoDao().insert(
                    PoskoEntity(
                        namaPosko = "Posko Logistik Bengkalis",
                        alamat = "Jl. Garuda No. 78, Bengkalis",
                        kapasitas = "250 Orang",
                        penanggungJawab = "BPBD Bengkalis",
                        telepon = "083456789012",
                        gambar = "https://images.unsplash.com/photo-1593113598332-cd288d649433?w=400&h=300&fit=crop"
                    )
                )

                // 4. Posko Medis Dumai
                db.poskoDao().insert(
                    PoskoEntity(
                        namaPosko = "Posko Medis Dumai",
                        alamat = "Jl. Basuki Rahmat No. 12, Dumai",
                        kapasitas = "400 Orang",
                        penanggungJawab = "RSUD Dumai",
                        telepon = "084567890123",
                        gambar = "https://images.unsplash.com/photo-1576091160399-112ba8d25d1d?w=400&h=300&fit=crop"
                    )
                )
            }
            loadData()
        }
    }

    private fun loadData() {
        viewLifecycleOwner.lifecycleScope.launch {
            val data = db.poskoDao().getAll()
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