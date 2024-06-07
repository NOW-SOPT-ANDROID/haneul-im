package com.sopt.now

import android.text.TextUtils
import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.ItemFriendBinding

class FriendViewHolder(private val binding: ItemFriendBinding) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(friendData: Friend) {
        binding.run {
            ivProfile.setImageResource(friendData.profileImage)
            tvName.text = friendData.name
            tvSelfDescription.maxWidth = 500
            tvSelfDescription.text = if (friendData.selfDescription.length > 30) {
                TextUtils.ellipsize(friendData.selfDescription, tvSelfDescription.paint, 200f, TextUtils.TruncateAt.END)
            } else {
                friendData.selfDescription
            }
        }
    }
}