package com.example.alya_love.pertemuan_10

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPoskoBencanaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        db = AppDatabase.getDatabase(requireContext())

        binding.fabTambahPosko.setOnClickListener {

            startActivity(
                Intent(
                    requireContext(),
                    TambahPoskoActivity::class.java
                )
            )
        }

        adapter = PoskoAdapter(
            dataList
        ) { posko ->

            viewLifecycleOwner.lifecycleScope.launch {

                db.poskoDao().delete(posko)

                loadData()
            }
        }

        binding.rvPoskoBencana.layoutManager =
            LinearLayoutManager(requireContext())

        binding.rvPoskoBencana.adapter = adapter

        insertDummyData()
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun insertDummyData() {

        viewLifecycleOwner.lifecycleScope.launch {

            if (db.poskoDao().getAll().isEmpty()) {

                db.poskoDao().insert(
                    PoskoEntity(
                        namaPosko = "Posko Utama Pekanbaru",
                        alamat = "Jl. Sudirman Pekanbaru",
                        kapasitas = "500 Orang",
                        penanggungJawab = "BPBD Riau",
                        telepon = "08123456789",
                        gambar = ""
                    )
                )

                db.poskoDao().insert(
                    PoskoEntity(
                        namaPosko = "Posko Evakuasi Siak",
                        alamat = "Jl. Hangtuah Siak",
                        kapasitas = "300 Orang",
                        penanggungJawab = "Relawan Siaga",
                        telepon = "08234567890",
                        gambar = ""
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