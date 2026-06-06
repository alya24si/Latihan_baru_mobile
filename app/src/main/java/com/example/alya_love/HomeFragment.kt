package com.example.alya_love

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alya_love.databinding.FragmentHomeBinding
import com.example.alya_love.news.NewsAdapter
import com.example.alya_love.news.NewsResponse
import com.example.alya_love.news.RetrofitClient
import com.example.alya_love.pertemuan_10.TenthActivity
import com.example.alya_love.pertemuan_3.ThirdActivity
import com.example.alya_love.pertemuan_3.ThirdResultActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // Rumus Bangun Ruang
        binding.btnRumus.setOnClickListener {

            val intent = Intent(
                requireContext(),
                bangun_ruang::class.java
            )

            intent.putExtra(
                "judul",
                "Mobileku"
            )

            intent.putExtra(
                "deskripsi",
                "Ini aplikasi punya Alya"
            )

            startActivity(intent)
        }

        // Custom 1
        binding.btnCustom1.setOnClickListener {

            val intent = Intent(
                requireContext(),
                ThirdResultActivity::class.java
            )

            intent.putExtra(
                "judul",
                "Mobileku"
            )

            intent.putExtra(
                "deskripsi",
                "Ini aplikasi punya Alya"
            )

            startActivity(intent)
        }

        // Web Bina Desa
        binding.btnStart.setOnClickListener {

            val intent =
                Intent(Intent.ACTION_VIEW)

            intent.data =
                Uri.parse(
                    "https://alya-project.alwaysdata.net/dashboard-guest"
                )

            startActivity(intent)
        }

        // Pertemuan 10
        binding.btnPertemuan10.setOnClickListener {

            startActivity(
                Intent(
                    requireContext(),
                    TenthActivity::class.java
                )
            )
        }

        // Logout
        binding.btnLogout.setOnClickListener {

            AlertDialog.Builder(requireContext())
                .setTitle("Konfirmasi Logout")
                .setMessage("Apakah ingin logout?")
                .setPositiveButton("Ya") { _, _ ->

                    requireActivity()
                        .getSharedPreferences(
                            "LOGIN",
                            android.content.Context.MODE_PRIVATE
                        )
                        .edit()
                        .clear()
                        .apply()

                    startActivity(
                        Intent(
                            requireContext(),
                            ThirdActivity::class.java
                        )
                    )

                    requireActivity().finish()
                }

                .setNegativeButton("Tidak") { dialog, _ ->
                    dialog.dismiss()
                }

                .show()
        }

        // Load Berita API
        loadNews()
    }

    private fun loadNews() {

        RetrofitClient.apiService
            .getPosts()
            .enqueue(object : Callback<NewsResponse> {

                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {

                    if (response.isSuccessful) {

                        val list =
                            response.body()?.posts
                                ?: emptyList()

                        binding.rvNews.layoutManager =
                            LinearLayoutManager(
                                requireContext()
                            )

                        binding.rvNews.adapter =
                            NewsAdapter(list)
                    }
                }

                override fun onFailure(
                    call: Call<NewsResponse>,
                    t: Throwable
                ) {

                    t.printStackTrace()
                }
            })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}