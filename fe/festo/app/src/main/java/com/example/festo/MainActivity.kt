package com.example.festo

import RetrofitClient
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.festo.customer_ui.home.HomeActivity
import com.example.festo.customer_ui.orderlist.OrderlistFragment
import com.example.festo.data.API.UserAPI
import com.example.festo.data.req.LoginReq
import com.example.festo.data.res.LoginRes
import com.example.festo.databinding.ActivityMainBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    val api = UserAPI.create()
    private lateinit var binding: ActivityMainBinding
    @SuppressLint("StringFormatInvalid")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SharedPreferences 인스턴스 생성
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        // 입력될 값의 타입에 맞는 Editor 써서 저장해야함
        val editor = sharedPreferences.edit()


        // 로그인 정보 확인
        UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
            if (error != null) {
//                Toast.makeText(this, "토큰 정보 보기 실패", Toast.LENGTH_SHORT).show()
            }
            else if (tokenInfo != null) {
//                Log.i("엑세스 유지: ", tokenInfo.toString())
//                Toast.makeText(this, "토큰 정보 보기 성공", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }

        val keyHash = Utility.getKeyHash(this)
//        Log.d("Hash", keyHash)


        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
//                Log.i(TAG, "test " + error.toString())
                // Log.i(TAG, "test $error")
                when {
                    error.toString() == AuthErrorCause.AccessDenied.toString() -> {
                        Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidClient.toString() -> {
                        Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidGrant.toString() -> {
                        Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidRequest.toString() -> {
                        Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.InvalidScope.toString() -> {
                        Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Misconfigured.toString() -> {
                        Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.ServerError.toString() -> {
                        Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
                    }
                    error.toString() == AuthErrorCause.Unauthorized.toString() -> {
                        Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
                    }
                    else -> { // Unknown
                        Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else if (token != null) {
                Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
                // Log.i("토큰: ", token.toString())
                // 카카오 SDK 유저정보 불러오기
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
//                        Log.i("카카오 유저정보","사용자 정보 요청 실패 $error")
                    } else if (user != null) {
//                        Log.i("카카오 유저정보", "사용자 정보 요청 성공 $user")
                        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                        val fcmToken = sharedPreferences.getString("FCM_TOKEN", "")
                        if (fcmToken != null) {
//                            Log.i("fcm토큰 DB쏘기", fcmToken)
                        }

                        // request 보낼 데이터 정의
                        val request = LoginReq(
                            authId = user.id,
                            nickname = user?.kakaoAccount?.profile?.nickname,
                            profileImageUrl = user?.kakaoAccount?.profile?.profileImageUrl,
                            fcmDeviceToken = fcmToken
                        )
                        // request 보내고 콜백으로 LoginRes 데이터파일에 맞게 받아오기
                        api.login(request).enqueue(object : retrofit2.Callback<LoginRes> {
                            override fun onResponse(call: Call<LoginRes>, response: Response<LoginRes>) {
//                                Log.i("토큰 받기", "refresh_token ${response.body()}")
                                // 받은 토큰 sharedprefereces로 저장하기
                                editor.putString("access_token", response.body()?.accessToken)
                                editor.putString("myToken", response.body()?.refreshToken)
                                editor.putString("memberId", response.body()?.memberId)
                                editor.apply()
                            }
                            override fun onFailure(call: Call<LoginRes>, t: Throwable) {
//                                Log.i("정보요청 실패", "$t")
                            }
                        })
                    }
                }
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                finish()
            }
        }

        binding.kakaoLoginButton.setOnClickListener {
            if(UserApiClient.instance.isKakaoTalkLoginAvailable(this@MainActivity)){
                UserApiClient.instance.loginWithKakaoTalk(this@MainActivity, callback = callback)


            }else{
                UserApiClient.instance.loginWithKakaoAccount(this@MainActivity, callback = callback)
            }
        }


    }

}