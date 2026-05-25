package com.example.alya_love.pertemuan_10

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alya_love.databinding.FragmentDonasiBencanaBinding

class DonasiBencanaFragment : Fragment() {

    private var _binding: FragmentDonasiBencanaBinding? = null
    private val binding get() = _binding!!

    private val dataList = listOf(
        Donasi(1, "Bagya Hidayat", "Sembako", "Rp 4,717,265", "Angin Puting Beliung", "https://picsum.photos/seed/donasi1/400/300"),
        Donasi(2, "Zelda Novitasari S.Psi", "Sembako", "Rp 1,635,669", "Gempa Bumi", "https://picsum.photos/seed/donasi2/400/300"),
        Donasi(3, "Septi Lestari S.T.", "Obat-obatan", "Rp 3,205,378", "Tanah Longsor", "https://picsum.photos/seed/donasi3/400/300")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDonasiBencanaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = DonasiAdapter(
            dataList,
            onItemClick = { },
            onEditClick = { },
            onDeleteClick = { }
        )

        binding.rvDonasiBencana.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}