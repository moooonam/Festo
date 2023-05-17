package com.nowusee.festo.booth_ui.no_booth

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nowusee.festo.R
import com.nowusee.festo.customer_ui.home.HomeActivity
import com.nowusee.festo.data.API.BoothAPI
import com.nowusee.festo.data.API.UserAPI
import com.nowusee.festo.data.res.FestivalIdRes
import com.nowusee.festo.data.res.MyBoothListRes
import com.nowusee.festo.databinding.FragmentNoBoothMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NoBoothMainFragment : Fragment() {
    private var mBinding: FragmentNoBoothMainBinding? = null
    private var retrofit = RetrofitClient.client
    private lateinit var listAdapter: RegisteredFestivalListAdapter
    private var myBoothList = emptyList<MyBoothListRes>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentNoBoothMainBinding.inflate(inflater, container, false)
        mBinding = binding

        val sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val myValue = sharedPreferences.getString("myToken", "")
        val token = "$myValue"
        // 축제 코드 확인 후 부스 등록 페이지로 이동
        mBinding!!.goRegister.setOnClickListener {
            var code = mBinding!!.festivalCode.text.toString()
            val postApi = retrofit?.create(BoothAPI::class.java)
            postApi!!.getFestivalCodeCheck(token, code).enqueue(object : Callback<FestivalIdRes> {
                override fun onResponse(
                    call: Call<FestivalIdRes>,
                    response: Response<FestivalIdRes>
                ) {
                    if (response.isSuccessful) {
//                        println("성공!!!!!!!!!!!!!!!!!!!")
//                        Log.d(" 테스트", "${response.body()?.festivalId}")
//                        Toast.makeText(activity, "축제 코드 확인 성공", Toast.LENGTH_SHORT).show()
                        val transaction = fragmentManager?.beginTransaction()
                        val bundle = Bundle().apply { putString("festivalId", response.body()?.festivalId.toString()) }
                        val fragment = RegisterBoothFragment().apply { arguments = bundle }
                        transaction?.replace(
                            R.id.no_booth_layout_nav_bottom,
                            fragment
                        )
//                        transaction?.replace(R.id.no_booth_layout_nav_bottom, BoothHomeFragment())
                        transaction?.commit()
                    } else {
                        Toast.makeText(activity, "축제 코드를 확인해 주세요", Toast.LENGTH_SHORT).show()
                    }
                }
                override fun onFailure(call: Call<FestivalIdRes>, t: Throwable) {
//                    println("실패!!!!!!!!!!!!!!!!!!!")
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 나의 부스 리스트 연결
        val userpostApi = retrofit?.create(UserAPI::class.java)
        val sharedPreferences =
            requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val myToken = sharedPreferences.getString("myToken", "")
        val memberId  = sharedPreferences.getString("memberId","")
        val token= "$myToken"
        val myId = "$memberId"
        userpostApi!!.getMyBoothList(token, myId).enqueue(object : Callback<List<MyBoothListRes>> {
            override fun onResponse(
                call: Call<List<MyBoothListRes>>,
                response: Response<List<MyBoothListRes>>
            ) {
                if (response.isSuccessful) {
//                    println("성공!!!!!!!!!!!!!!!!!!!")
//                    println(response.body())
//                    Log.d("나의 부스 리스트 조회", "${response.body()?.get(0)?.boothId}")
                    myBoothList = response.body() ?: emptyList()
                    listAdapter = RegisteredFestivalListAdapter(myBoothList)
                    mBinding?.festivalRecyclerView?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                    mBinding?.festivalRecyclerView?.adapter = listAdapter
                } else {
                    Log.d("나의 부스 리스트 조회 실패", "${response.body()}")
                }
            }

            override fun onFailure(call: Call<List<MyBoothListRes>>, t: Throwable) {
//                println("나의 부스 리스트 조회 실패!!!!!!!!!!!!!!!!!!!")
                t.printStackTrace()
            }
        })
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}

