package com.example.alya_love.pertemuan_10

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alya_love.databinding.FragmentLogistikBinding

class LogistikFragment : Fragment() {

    private var _binding: FragmentLogistikBinding? = null
    private val binding get() = _binding!!

    private val dataList = listOf(
        Logistik(1, "et", "Dus", 191, "Perum Oktaviani Tbk", "Tsunami"),
        Logistik(2, "molestiae", "Liter", 131, "CV Mandasari", "Angin Puting Beliung"),
        Logistik(3, "inventore", "Kg", 87, "PD Tampubolon (Persero) Tbk", "Angin Puting Beliung"),
        Logistik(4, "esse", "Kg", 38, "PD Lazuardi", "Tanah Longsor"),
        Logistik(5, "enim", "Dus", 88, "Yayasan Sudiati Wastuti", "Tanah Longsor")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLogistikBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = LogistikAdapter(
            dataList,
            onItemClick = { },
            onEditClick = { },
            onDeleteClick = { }
        )

        binding.rvLogistik.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}