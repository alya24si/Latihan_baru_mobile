package com.example.alya_love.pertemuan_10

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alya_love.databinding.FragmentKejadianBencanaBinding

class KejadianBencanaFragment : Fragment() {

    private var _binding: FragmentKejadianBencanaBinding? = null
    private val binding get() = _binding!!

    private val dataList = listOf(
        Bencana(1, "Banjir", "Fugit tempore molestiae explicabo at. Rerum neque quos illo nihil est qui. Sunt...", "Sawahlunto", "2026-01-07", "https://picsum.photos/seed/banjir1/400/300"),
        Bencana(2, "Tsunami", "Cupiditate consequatur eos dignissimos commodi nulla sequi. Dolores assumenda eo...", "Pematangsiantar", "2025-12-27", "https://picsum.photos/seed/tsunami1/400/300"),
        Bencana(3, "Tsunami", "Eveniet porro qui sunt laboriosam dolore et quisquam. Quis quo commodi aut eum e...", "Tual", "2025-11-28", "https://picsum.photos/seed/tsunami2/400/300")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentKejadianBencanaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = BencanaAdapter(
            dataList,
            onItemClick = { },
            onEditClick = { },
            onDeleteClick = { }
        )

        binding.rvKejadianBencana.apply {
            layoutManager = LinearLayoutManager(requireContext())
            this.adapter = adapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}