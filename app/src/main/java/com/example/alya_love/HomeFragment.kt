package com.example.alya_love

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.alya_love.databinding.FragmentHomeBinding  // ← Import binding
import com.example.alya_love.pertemuan_3.CustomActivity
import com.example.alya_love.pertemuan_3.ThirdActivity
import com.example.alya_love.pertemuan_3.ThirdResultActivity
import com.example.alya_love.pertemuan_10.TenthActivity  // ← Import baru
import com.google.android.material.snackbar.Snackbar

class HomeFragment : Fragment() {

    // 🔹 Deklarasi binding (nullable karena Fragment lifecycle)
    private var _binding: FragmentHomeBinding? = null
    // 🔹 Getter untuk mengakses binding (hanya valid saat view ada)
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // 🔹 Inflate binding
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 🔹 Tombol Rumus → Pindah ke bangun_ruang
        binding.btnRumus.setOnClickListener {
            val intent = Intent(requireContext(), bangun_ruang::class.java)
            intent.putExtra("judul", "Mobileku")
            intent.putExtra("deskripsi", "Ini aplikasi punya Alya")
            startActivity(intent)
        }

        // 🔹 Tombol Custom 1 → Pindah ke ThirdResultActivity
        binding.btnCustom1.setOnClickListener {
            val intent = Intent(requireContext(), ThirdResultActivity::class.java)
            intent.putExtra("judul", "Mobileku")
            intent.putExtra("deskripsi", "Ini aplikasi punya Alya")
            startActivity(intent)
        }

        // 🔹 Tombol Start → Buka Website
        binding.btnStart.setOnClickListener {
            val url = "https://alya-project.alwaysdata.net/dashboard-guest"
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            startActivity(intent)
        }

        // 🔹 Tombol Logout → Kembali ke login
        binding.btnLogout.setOnClickListener {
            AlertDialog.Builder(requireContext())
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah ingin logout?")
                .setPositiveButton("Ya") { _, _ ->
                    val sharedPref = requireActivity().getSharedPreferences("LOGIN", android.content.Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.clear()
                    editor.apply()

                    val intent = Intent(requireContext(), ThirdActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        // 🔹 🔥 BARU: Tombol Pertemuan 10 → Buka TenthActivity
        binding.btnPertemuan10.setOnClickListener {
            val intent = Intent(requireContext(), TenthActivity::class.java)
            startActivity(intent)
        }
    }

    // 🔹 PENTING: Null-kan binding saat view dihancurkan (mencegah memory leak)
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}