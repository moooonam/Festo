package com.example.festo.booth_ui.mypage



import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.festo.booth_ui.BoothMainActivity
import com.example.festo.booth_ui.home.BoothHomeFragment
import com.example.festo.customer_ui.home.HomeActivity
import com.example.festo.data.API.UserAPI
import com.example.festo.data.res.IsHaveFestivalRes
import com.example.festo.data.res.UserInfoRes
import com.example.festo.databinding.FragmentBoothMypageBinding
import com.example.festo.host_ui.HostMainActivity
import com.example.festo.host_ui.no_festival.NoFeativalMainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoothMypageFragment : Fragment() {
    private var retrofit = RetrofitClient.client
    private var mBinding : FragmentBoothMypageBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentBoothMypageBinding.inflate(inflater, container, false)

        mBinding = binding
        fun getUserData() {
            val sharedPreferences =
                requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val myToken = sharedPreferences.getString("myToken", "")
            val token = "$myToken"
            val postApi = retrofit?.create(UserAPI::class.java)
            postApi!!.getUserInfo(token).enqueue(object : Callback<UserInfoRes> {
                override fun onResponse(
                    call: Call<UserInfoRes>, response: Response<UserInfoRes>
                ) {
                    if (response.isSuccessful) {
                        Log.d(" 유저정보부르기", "${response},  ${response.body()?.nickname}")
                        mBinding?.tvGreating2!!.text = "${response.body()?.nickname}님 안녕하세요"
                    }

                }

                override fun onFailure(call: Call<UserInfoRes>, t: Throwable) {
                    Log.d(" 부스 주문내역 실패", "응")
                    t.printStackTrace()
                }
            })
        }
        getUserData()
        mBinding!!.ivProfile1.setOnClickListener{
            val intent = Intent(getActivity(), HomeActivity::class.java)
            startActivity(intent)
        }
        mBinding!!.ivProfile3.setOnClickListener{
            fun getIsHave() {

                val sharedPreferences =
                    requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                val myToken = sharedPreferences.getString("myToken", "")
                val memberId = sharedPreferences.getString("memberId", "")
                val token = "$myToken"
                val myId = "$memberId"
                Log.d("아이디", "${myId}")
                val postApi = retrofit?.create(UserAPI::class.java)
                postApi!!.getIsHaveFestival(token, myId).enqueue(object :
                    Callback<IsHaveFestivalRes> {
                    override fun onResponse(
                        call: Call<IsHaveFestivalRes>,
                        response: Response<IsHaveFestivalRes>
                    ) {
                        if (response.isSuccessful) {
                            val isFestival = response.body()?.open
                            Log.d(" 있나없나", "${response},  ${response.body()?.open}")
                            if (isFestival!!) {
                                val intent =
                                    Intent(getActivity(), NoFeativalMainActivity::class.java)
                                startActivity(intent)
                            } else {
                                val intent = Intent(getActivity(), HostMainActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    }

                    override fun onFailure(call: Call<IsHaveFestivalRes>, t: Throwable) {
                        Log.d(" 부스 주문내역 실패", "응")
                        t.printStackTrace()
                    }
                })
            }
            getIsHave()
        }
        return  mBinding?.root
    }


    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}