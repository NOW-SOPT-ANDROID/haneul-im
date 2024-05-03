package com.sopt.now

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.sopt.now.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<HomeViewModel>()

    // 프로필과 프로필 어댑터를 멤버 변수로 선언합니다.
    private val profile = Profile(R.drawable.dog1, "임하늘", "시험 시러")
    private val profileAdapter = ProfileAdapter(profile) {
        showEditDescriptionDialog(profile)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val friendAdapter = FriendAdapter()

        // 친구 어댑터와 프로필 어댑터를 결합하여 ConcatAdapter를 생성합니다.
        val concatAdapter = ConcatAdapter(profileAdapter, friendAdapter)

        binding.rvFriends.run {
            adapter = concatAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        friendAdapter.setFriendList(viewModel.mockFriendList)
    }

    private fun showEditDescriptionDialog(profile: Profile) {
        val editText = EditText(requireContext())
        editText.setText(profile.selfDescription)

        AlertDialog.Builder(requireContext())
            .setTitle("상태 메세지를 입력해주세요")
            .setView(editText)
            .setPositiveButton("Save") { _, _ ->
                val newDescription = editText.text.toString()
                profile.updateDescription(newDescription)
                profileAdapter.notifyDataSetChanged()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}