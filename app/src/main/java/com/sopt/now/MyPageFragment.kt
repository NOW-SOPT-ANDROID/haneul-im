package com.sopt.now

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sopt.now.databinding.FragmentMypageBinding



class MyPageFragment: Fragment() {
    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nickname = arguments?.getString("nickname", "")?.toUpperCase()
        val mbti = arguments?.getString("mbti", "")?.toUpperCase()
        val id = arguments?.getString("id", "")
        val password = arguments?.getString("password", "")
        val city = arguments?.getString("city", "")?.toUpperCase()

        binding.tvNickname.text = nickname
        binding.tvMbti2.text = mbti
        binding.tvId2.text = id
        binding.tvPassword2.text = password
        binding.tvCity.text = city
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}