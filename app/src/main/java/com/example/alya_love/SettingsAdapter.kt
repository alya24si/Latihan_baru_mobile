// SettingsAdapter.kt
package com.example.alya_love.settings

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.alya_love.R

data class InfoItem(val title: String, val desc: String, val iconRes: Int, val category: String)

class SettingsAdapter(
    private val context: Context,
    private val allItems: List<InfoItem>,  // ✅ Simpan data asli (immutable)
    private val onItemClick: (InfoItem) -> Unit
) : BaseAdapter() {

    // ✅ List yang akan ditampilkan (bisa berubah-ubah)
    private var displayItems: List<InfoItem> = allItems

    override fun getCount() = displayItems.size
    override fun getItem(position: Int) = displayItems[position]
    override fun getItemId(position: Int) = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_info_simple, parent, false)

        val item = displayItems[position]
        view.findViewById<ImageView>(R.id.ivIcon).setImageResource(item.iconRes)
        view.findViewById<TextView>(R.id.tvTitle).text = item.title
        view.findViewById<TextView>(R.id.tvDesc).text = item.desc

        view.setOnClickListener { onItemClick(item) }
        return view
    }

    // ✅ Fungsi filter yang benar: selalu filter dari allItems
    fun filterByCategory(category: String) {
        displayItems = if (category == "Semua") {
            allItems  // ✅ Tampilkan semua dari data asli
        } else {
            allItems.filter { it.category == category }  // ✅ Filter dari data asli
        }
        notifyDataSetChanged()  // ✅ Refresh ListView
    }

    // ✅ Optional: method untuk reset ke semua
    fun showAll() {
        displayItems = allItems
        notifyDataSetChanged()
    }
}