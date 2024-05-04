package com.sopt.now

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.sopt.now.ServicePool.authService
import com.sopt.now.compose.RequestChangePasswordDto
import com.sopt.now.compose.ResponseChangePasswordDto
import com.sopt.now.databinding.FragmentMypageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        val memberId = arguments?.getString("memberId", "")
        binding.btChange.setOnClickListener {
            val previous = binding.etPrevious.text.toString()
            val new1 = binding.etNew1.text.toString()
            val new2 = binding.etNew2.text.toString()

            if (new1 != new2) {
                Toast.makeText(context, "새로운 비밀번호가 서로 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val changePasswordRequest = RequestChangePasswordDto(
                previousPassword = previous,
                newPassword = new1,
                newPasswordVerification = new2
            )
            if (memberId != null) {
                authService.changePassword(memberId, changePasswordRequest).enqueue(object :
                    Callback<ResponseChangePasswordDto> {
                    override fun onResponse(
                        call: Call<ResponseChangePasswordDto>,
                        response: Response<ResponseChangePasswordDto>
                    ) {
                        if (response.isSuccessful) {
                            Toast.makeText(
                                context,
                                "비밀번호가 변경되었습니다! 다시 로그인 해주세요 !",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(Intent(context, LoginActivity::class.java))
                        } else {
                            Toast.makeText(context, "비밀번호 변경에 실패했습니다ㅠ", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponseChangePasswordDto>, t: Throwable) {
                        Toast.makeText(context, "서버 에러 발생", Toast.LENGTH_SHORT).show()
                        Log.e("PasswordActivity", "에러 : ${t.message}", t)
                    }
                })
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
