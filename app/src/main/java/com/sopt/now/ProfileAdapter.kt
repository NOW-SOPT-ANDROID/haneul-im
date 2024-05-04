package com.sopt.now

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sopt.now.databinding.ItemProfileBinding

class ProfileAdapter(
    private val profile: Profile,
    private val onEditButtonClick: () -> Unit
) : RecyclerView.Adapter<ProfileViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProfileBinding.inflate(inflater, parent, false)
        return ProfileViewHolder(binding, onEditButtonClick)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        holder.onBind(profile)
    }

    override fun getItemCount() = 1
}