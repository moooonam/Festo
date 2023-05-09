package com.example.festo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.festo.databinding.ActivitySecondBinding
import com.kakao.sdk.user.UserApi
import com.kakao.sdk.user.UserApiClient

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val testToken = sharedPreferences.getString("access_token", "")
        val testrefToken = sharedPreferences.getString("refresh_token", "")
        binding.testToken.text = "토큰 : $testToken"
        binding.testRefToken.text = "ref 토큰 : $testrefToken"

        UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.i("정보","사용자 정보 요청 실패 $error")
            } else if (user != null) {
                Log.i("정보", "사용자 정보 요청 성공 $user")
                binding.tvNick.text = "닉네임 : ${user?.kakaoAccount?.profile?.nickname}"
            }
        }

        binding.kakaoLogoutButton.setOnClickListener {
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Toast.makeText(this,"로그아웃 실패 $error", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(this,"로그아웃 성공", Toast.LENGTH_SHORT).show()
                }
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }

        binding.kakaoUnlinkButton.setOnClickListener {
            UserApiClient.instance.unlink { error ->
                if (error != null) {
                    Toast.makeText(this, "회원 탈퇴 실패 $error", Toast.LENGTH_SHORT).show()
                }else {
                    Toast.makeText(this, "회원 탈퇴 성공", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent.addFlags(FLAG_ACTIVITY_CLEAR_TOP))
                    finish()
                }
            }
        }

    }
}