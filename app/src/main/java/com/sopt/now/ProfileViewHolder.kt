package com.sopt.now

import android.text.TextUtils
import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.ItemProfileBinding

class ProfileViewHolder(
    private val binding: ItemProfileBinding,
    private val onEditButtonClick: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(profileData: Profile) {
        binding.run {
            ivProfile.setImageResource(profileData.profileImage)
            tvName.text = profileData.name
            tvSelfDescription.maxWidth = 50
            tvSelfDescription.text = if (profileData.selfDescription.length > 30) {
                TextUtils.ellipsize(profileData.selfDescription, tvSelfDescription.paint, 200f, TextUtils.TruncateAt.END)
            } else {
                profileData.selfDescription
            }
            btnEdit.setOnClickListener {
                onEditButtonClick.invoke()
            }
        }
    }
}