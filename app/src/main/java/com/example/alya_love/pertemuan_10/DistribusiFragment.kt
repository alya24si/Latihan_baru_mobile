package com.example.alya_love.pertemuan_10

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alya_love.databinding.FragmentDistribusiBinding

class DistribusiFragment : Fragment() {

    private var _binding: FragmentDistribusiBinding? = null
    private val binding get() = _binding!!

    private val dataList = listOf(
        Distribusi(1, "Paket Sembako", "500 Paket", "Desa Sukamaju", "2026-01-10", "Tersalurkan"),
        Distribusi(2, "Obat-obatan", "200 Box", "Rumah Sakit Umum", "2026-01-11", "Dalam Proses"),
        Distribusi(3, "Selimut", "300 Pcs", "Pengungsian A", "2026-01-12", "Tersalurkan")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDistribusiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DistribusiAdapter(
            dataList,
            onItemClick = { },
            onEditClick = { },
            onDeleteClick = { }
        )

        binding.rvDistribusi.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}