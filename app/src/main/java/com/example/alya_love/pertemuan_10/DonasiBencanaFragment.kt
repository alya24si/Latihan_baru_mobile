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
import com.example.alya_love.databinding.FragmentDonasiBencanaBinding
import com.example.alya_love.room.AppDatabase
import com.example.alya_love.room.DonasiEntity
import kotlinx.coroutines.launch

class DonasiBencanaFragment : Fragment() {

    private var _binding: FragmentDonasiBencanaBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: AppDatabase
    private lateinit var adapter: DonasiAdapter

    private val dataList = mutableListOf<DonasiEntity>()

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
        _binding = FragmentDonasiBencanaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getDatabase(requireContext())

        binding.fabAdd.setOnClickListener {
            startActivity(
                Intent(requireContext(), DonasiFormActivity::class.java)
            )
        }

        // Di bagian adapter, ubah menjadi:

        adapter = DonasiAdapter(
            dataList,
            onItemClick = { donasi ->
                // Lihat Detail
                val intent = Intent(requireContext(), DetailDonasiActivity::class.java)
                intent.putExtra("EXTRA_ID", donasi.id)
                startActivity(intent)
            },
            onEditClick = { donasi ->
                // Edit
                val intent = Intent(requireContext(), DonasiFormActivity::class.java)
                intent.putExtra("EXTRA_ID", donasi.id)
                editLauncher.launch(intent)
            },
            onDeleteClick = { donasi ->
                showDeleteConfirmation(donasi)
            }
        )

        binding.rvDonasiBencana.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDonasiBencana.adapter = adapter

        insertDummyData()
    }

    private fun showDeleteConfirmation(donasi: DonasiEntity) {
        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Donasi?")
            .setMessage("Apakah Anda yakin ingin menghapus donasi dari \"${donasi.namaDonatur}\"?")
            .setPositiveButton("Hapus") { _, _ ->
                viewLifecycleOwner.lifecycleScope.launch {
                    db.donasiDao().delete(donasi)
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
            if (db.donasiDao().getAll().isEmpty()) {
                db.donasiDao().insert(
                    DonasiEntity(
                        namaDonatur = "Bagya Hidayat",
                        jenisDonasi = "Sembako",
                        nilai = "Rp 4.717.265",
                        kejadian = "Angin Puting Beliung"
                    )
                )
                db.donasiDao().insert(
                    DonasiEntity(
                        namaDonatur = "Zelda Novitasari S.Psi",
                        jenisDonasi = "Sembako",
                        nilai = "Rp 1.635.669",
                        kejadian = "Gempa Bumi"
                    )
                )
                db.donasiDao().insert(
                    DonasiEntity(
                        namaDonatur = "Septi Lestari S.T.",
                        jenisDonasi = "Obat-obatan",
                        nilai = "Rp 3.205.378",
                        kejadian = "Tanah Longsor"
                    )
                )
                db.donasiDao().insert(
                    DonasiEntity(
                        namaDonatur = "Ahmad Rizki",
                        jenisDonasi = "Pakaian Layak Pakai",
                        nilai = "Rp 2.500.000",
                        kejadian = "Banjir"
                    )
                )
                db.donasiDao().insert(
                    DonasiEntity(
                        namaDonatur = "Siti Nurhaliza",
                        jenisDonasi = "Uang Tunai",
                        nilai = "Rp 5.000.000",
                        kejadian = "Kebakaran"
                    )
                )
            }
            loadData()
        }
    }

    private fun loadData() {
        viewLifecycleOwner.lifecycleScope.launch {
            val data = db.donasiDao().getAll()
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