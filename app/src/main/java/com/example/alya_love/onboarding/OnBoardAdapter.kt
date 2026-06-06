package com.example.alya_love.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alya_love.databinding.ItemOnboardingBinding

class OnBoardAdapter(
    private val list: List<OnBoardItem>
) : RecyclerView.Adapter<OnBoardAdapter.ViewHolder>() {

    inner class ViewHolder(
        val binding: ItemOnboardingBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val binding =
            ItemOnboardingBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val item = list[position]

        holder.binding.imgBoard.setImageResource(item.image)

        holder.binding.txtTitle.text = item.title

        holder.binding.txtDesc.text = item.description

        // Gambar muncul dari bawah
        holder.binding.imgBoard.translationY = 200f
        holder.binding.imgBoard.alpha = 0f

        holder.binding.imgBoard.animate()
            .translationY(0f)
            .alpha(1f)
            .setDuration(800)
            .start()

// Judul muncul perlahan
        holder.binding.txtTitle.alpha = 0f

        holder.binding.txtTitle.animate()
            .alpha(1f)
            .setDuration(1200)
            .start()

// Deskripsi muncul setelah judul
        holder.binding.txtDesc.alpha = 0f

        holder.binding.txtDesc.animate()
            .alpha(1f)
            .setStartDelay(500)
            .setDuration(1200)
            .start()

        // Animasi gambar zoom
        holder.binding.imgBoard.scaleX = 0f
        holder.binding.imgBoard.scaleY = 0f

        holder.binding.imgBoard.animate()
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(700)
            .start()

        // Animasi judul
        holder.binding.txtTitle.alpha = 0f

        holder.binding.txtTitle.animate()
            .alpha(1f)
            .setDuration(1200)
            .start()

        // Animasi deskripsi
        holder.binding.txtDesc.alpha = 0f

        holder.binding.txtDesc.animate()
            .alpha(1f)
            .setStartDelay(400)
            .setDuration(1200)
            .start()
    }
}