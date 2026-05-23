package com.example.alya_love.pertemuan_10  // ← Pastikan package sama!

import android.os.Bundle
import androidx.fragment.app.Fragment  // ← Import WAJIB ini!
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.alya_love.R  // ← Import R dari project utama

class TabAFragment : Fragment() {  // ← Harus extend androidx.fragment.app.Fragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate layout fragment_tab_a.xml
        return inflater.inflate(R.layout.fragment_tab_a, container, false)
    }
}