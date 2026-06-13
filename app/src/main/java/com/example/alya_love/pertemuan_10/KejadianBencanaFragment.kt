package com.example.alya_love.pertemuan_10

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alya_love.databinding.FragmentKejadianBencanaBinding
import com.example.alya_love.room.AppDatabase
import com.example.alya_love.room.BencanaEntity
import kotlinx.coroutines.launch

class KejadianBencanaFragment : Fragment() {

    private var _binding: FragmentKejadianBencanaBinding? = null
    private val binding get() = _binding!!

    private lateinit var db: AppDatabase
    private lateinit var adapter: BencanaAdapter

    private val dataList = mutableListOf<BencanaEntity>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentKejadianBencanaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getDatabase(requireContext())

        binding.fabTambah.setOnClickListener {
            startActivity(
                Intent(requireContext(), TambahBencanaActivity::class.java)
            )
        }

        adapter = BencanaAdapter(
            dataList,
            onItemClick = { },
            onEditClick = { },
            onDeleteClick = { bencana ->

                viewLifecycleOwner.lifecycleScope.launch {
                    db.bencanaDao().delete(bencana)
                    loadData()
                }
            }
        )

        binding.rvKejadianBencana.layoutManager =
            LinearLayoutManager(requireContext())

        binding.rvKejadianBencana.adapter = adapter

        insertDummyData()
    }


    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun insertDummyData() {

        viewLifecycleOwner.lifecycleScope.launch {

            if (db.bencanaDao().getAll().isEmpty()) {

                db.bencanaDao().insert(
                    BencanaEntity(
                        judul = "Banjir",
                        deskripsi = "Banjir akibat hujan deras",
                        lokasi = "Pekanbaru",
                        tanggal = "2026-01-07",
                        gambar = "https://picsum.photos/seed/banjir1/400/300"
                    )
                )

                db.bencanaDao().insert(
                    BencanaEntity(
                        judul = "Tsunami",
                        deskripsi = "Gelombang tinggi",
                        lokasi = "Padang",
                        tanggal = "2026-01-10",
                        gambar = "https://picsum.photos/seed/tsunami1/400/300"
                    )
                )
            }

            loadData()
        }
    }

    private fun loadData() {

        viewLifecycleOwner.lifecycleScope.launch {

            val data = db.bencanaDao().getAll()

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