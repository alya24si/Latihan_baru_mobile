package com.example.alya_love.pertemuan_10

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alya_love.databinding.FragmentPoskoBencanaBinding

class PoskoBencanaFragment : Fragment() {

    private var _binding: FragmentPoskoBencanaBinding? = null
    private val binding get() = _binding!!

    private val dataList = listOf(
        Posko(1, "PT Hartati", "Jr. Asia Afrika No. 441, Banjarmasin 74453, Kaltara", "0240 3485 772", "Agus Mangunsong", "https://picsum.photos/seed/posko1/400/300"),
        Posko(2, "Fa Prasasta Sudiati Tbk", "Gg. Kusmanto No. 9, Bogor 91935, Sulsel", "(+62) 901 1458 4504", "Tri Sihombing", "https://picsum.photos/seed/posko2/400/300"),
        Posko(3, "UD Hidayanto Usada", "Ds. Radio No. 827, Banda Aceh 65958, Bali", "(+62) 966 9666 812", "Sadina Yuliarti", "https://picsum.photos/seed/posko3/400/300")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPoskoBencanaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PoskoAdapter(
            dataList,
            onItemClick = { },
            onEditClick = { },
            onDeleteClick = { }
        )

        binding.rvPoskoBencana.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}