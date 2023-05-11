package com.example.festo.booth_ui.no_booth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.festo.R
import com.example.festo.booth_ui.home.BoothHomeFragment
import com.example.festo.customer_ui.home.HomeActivity
import com.example.festo.data.API.BoothAPI
import com.example.festo.data.API.UserAPI
import com.example.festo.data.res.BoothMenuListRes
import com.example.festo.data.res.FestivalIdRes
import com.example.festo.databinding.FragmentNoBoothMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoBoothMainFragment : Fragment() {
    private var mBinding: FragmentNoBoothMainBinding? = null
    private var retrofit = RetrofitClient.client

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentNoBoothMainBinding.inflate(inflater, container, false)
        mBinding = binding


        // 축제 코드 확인 후 부스 등록 페이지로 이동
        mBinding!!.goRegister.setOnClickListener {
            var code = mBinding!!.festivalCode.text.toString()
            val postApi = retrofit?.create(BoothAPI::class.java)
            postApi!!.getFestivalCodeCheck(code).enqueue(object : Callback<FestivalIdRes> {
                override fun onResponse(
                    call: Call<FestivalIdRes>,
                    response: Response<FestivalIdRes>
                ) {
                    if (response.isSuccessful) {
                        println("성공!!!!!!!!!!!!!!!!!!!")
                        Log.d(" 테스트", "${response.body()?.festivalId}")
                        Toast.makeText(activity, "축제 코드 확인 성공", Toast.LENGTH_SHORT).show()
                        val transaction = fragmentManager?.beginTransaction()
                        transaction?.replace(R.id.no_booth_layout_nav_bottom, RegisterBoothFragment())
//                        transaction?.replace(R.id.no_booth_layout_nav_bottom, BoothHomeFragment())
                        transaction?.commit()
                    } else {
                        Toast.makeText(activity, "축제 코드를 확인해 주세요", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<FestivalIdRes>, t: Throwable) {
                    println("실패!!!!!!!!!!!!!!!!!!!")
                    t.printStackTrace()
                }
            })
        }

        // 일반 유저 마이페이지로 이동
        mBinding!!.goCustomer.setOnClickListener {
            val intent = Intent(requireContext(), HomeActivity::class.java)
            intent.putExtra("fragment", "MypageFragment")
            startActivity(intent)
        }
        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}

