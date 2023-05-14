package com.example.festo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.net.DnsResolver.Callback
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.festo.data.API.UserAPI
import com.example.festo.data.req.LoginReq
import com.example.festo.data.res.LoginRes
import com.example.festo.databinding.ActivitySecondBinding
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Response

class SecondActivity : AppCompatActivity() {
    /*var retrofit: Retrofit? = RetrofitClient.client // RetrofitClient의 instance 불러오기
    var api : UserAPI = retrofit!!.create(UserAPI::class.java)*/
    val api = UserAPI.create();
    private lateinit var binding: ActivitySecondBinding

    @SuppressLint("SetTextI18n", "StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SharedPreferences 인스턴스 생성
        /*val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        // 입력될 값의 타입에 맞는 Editor 써서 저장해야함
        val editor = sharedPreferences.edit()*/

        // 저장한 데이터 사용 방법
        /*val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val acessToken = sharedPreferences.getString("access_token", "")
        val refToken = sharedPreferences.getString("refresh_token", "")
        binding.testToken.text = "토큰 : $acessToken"
        binding.testRefToken.text = "ref 토큰 : $refToken"*/

        /*UserApiClient.instance.me { user, error ->
            if (error != null) {
                Log.i("정보","사용자 정보 요청 실패 $error")
            } else if (user != null) {
                Log.i("정보", "사용자 정보 요청 성공 $user")
                binding.tvNick.text = "닉네임 : ${user?.kakaoAccount?.profile?.nickname}"
                val request = LoginReq(
                    authId = user.id,
                    nickName = user?.kakaoAccount?.profile?.nickname,
                    profileImgUrl = user?.kakaoAccount?.profile?.profileImageUrl
                )
                api.login(request).enqueue(object : retrofit2.Callback<LoginRes> {
                    override fun onResponse(call: Call<LoginRes>, response: Response<LoginRes>) {
                        Log.i("정보 성공", "코드코드 ${response.body()?.refreshToken}")
                        editor.putString("access_token", response.body()?.accessToken)
                        editor.putString("refresh_token", response.body()?.refreshToken)
                        editor.apply()
                    }

                    override fun onFailure(call: Call<LoginRes>, t: Throwable) {
                        Log.i("정보요청 실패", "$t")
                    }

                })
            }
        }*/

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