package com.example.festo.host_ui.home

import RetrofitClient
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.festo.R
import com.example.festo.booth_ui.orderlist.BoothOrderListAdapter
import com.example.festo.data.API.HostAPI
import com.example.festo.data.API.UserAPI
import com.example.festo.data.res.BoothOrderListRes
import com.example.festo.data.res.FestivalCodeRes
import com.example.festo.data.res.FestivalInfoRes
import com.example.festo.data.res.MyFestivalRes
import com.example.festo.databinding.FragmentHostHomeBinding
import kakao.d.o
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create
import java.lang.reflect.Array
import java.text.SimpleDateFormat
import java.util.Locale

class HostHomeFragment : Fragment() {
    private var mBinding: FragmentHostHomeBinding? = null
    private var retrofit = RetrofitClient.client

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentHostHomeBinding.inflate(inflater, container, false)
        mBinding = binding

        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var myFestivalId = ""
        val sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val myValue = sharedPreferences.getString("myToken", "")
        val token = "$myValue"
        val getApi =retrofit?.create(HostAPI::class.java)
        getApi!!.getMyFestival(token).enqueue(object : Callback<List<MyFestivalRes>> {
            override fun onResponse(call: Call<List<MyFestivalRes>>, response: Response<List<MyFestivalRes>>) {
                if (response.isSuccessful) {
                    myFestivalId = response.body()?.get(0)?.festivalId.toString()
//                    Log.d("테스트중", "onResponse: ${response.body()}")
                    Log.d(" 마이 축제", "${response},  ${response.body()?.get(0)}")
                    val postApi = retrofit?.create(UserAPI::class.java)
                    Log.d("페스티벌 아이디", "${myFestivalId}")
                    postApi!!.getFestivalDetail(token, "${myFestivalId}").enqueue(object : Callback<FestivalInfoRes> {
                        override fun onResponse(
                            call: Call<FestivalInfoRes>,
                            response: Response<FestivalInfoRes>
                        ) {
                            if (response.isSuccessful) {
                                println("성공!!!!!!!!!!!!!!!!!!!")
                                println(response.body()?.startDate)
                                Log.d(" 테스트", "${response.body()}")
                                val festivalName = view.findViewById<TextView>(R.id.festivalName)
                                val festivalAddress = view.findViewById<TextView>(R.id.festivalAddress)
                                val festivalPeriod = view.findViewById<TextView>(R.id.festivalPeriod)
                                val festivalImage = view.findViewById<ImageView>(R.id.festivalImage)

                                // 날짜 형식 변환
                                val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
                                val outputFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())

                                val startDateString = response.body()?.startDate
                                val startDate = inputFormat.parse(startDateString.toString())
                                val formattedStartDate = outputFormat.format(startDate)

                                val endDateString = response.body()?.endDate
                                val endDate = inputFormat.parse(endDateString.toString())
                                val formattedEndDate = outputFormat.format(endDate)

                                // 데이터 xml에 입력
                                festivalName.text = response.body()?.name
                                festivalAddress.text = response.body()?.address
                                festivalPeriod.text = "${formattedStartDate} ~ ${formattedEndDate}"

                                // 이미지 설정
                                Glide.with(context!!)
                                    .load(response.body()?.imageUrl)
                                    .into(festivalImage)
                            }
                        }

                        override fun onFailure(call: Call<FestivalInfoRes>, t: Throwable) {
                            println("실패!!!!!!!!!!!!!!!!!!!")
                            t.printStackTrace()
                        }
                    })
                }

            }
            override fun onFailure(call: Call<List<MyFestivalRes>>, t: Throwable) {
                Log.d(" 마이축제 실패", "응")
                t.printStackTrace()
            }
        })
        val postApi = retrofit?.create(UserAPI::class.java)
        Log.d("페스티벌 아이디", "${myFestivalId}")
        postApi!!.getFestivalDetail(token, "${myFestivalId}").enqueue(object : Callback<FestivalInfoRes> {
            override fun onResponse(
                call: Call<FestivalInfoRes>,
                response: Response<FestivalInfoRes>
            ) {
                if (response.isSuccessful) {
                    println("성공!!!!!!!!!!!!!!!!!!!")
                    println(response.body()?.startDate)
                    Log.d(" 테스트", "${response.body()}")
                    val festivalName = view.findViewById<TextView>(R.id.festivalName)
                    val festivalAddress = view.findViewById<TextView>(R.id.festivalAddress)
                    val festivalPeriod = view.findViewById<TextView>(R.id.festivalPeriod)
                    val festivalImage = view.findViewById<ImageView>(R.id.festivalImage)

                    // 날짜 형식 변환
                    val inputFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
                    val outputFormat = SimpleDateFormat("yyyy.MM.dd", Locale.getDefault())

                    val startDateString = response.body()?.startDate
                    val startDate = inputFormat.parse(startDateString.toString())
                    val formattedStartDate = outputFormat.format(startDate)

                    val endDateString = response.body()?.endDate
                    val endDate = inputFormat.parse(endDateString.toString())
                    val formattedEndDate = outputFormat.format(endDate)

                    // 데이터 xml에 입력
                    festivalName.text = response.body()?.name
                    festivalAddress.text = response.body()?.address
                    festivalPeriod.text = "${formattedStartDate} ~ ${formattedEndDate}"

                    // 이미지 설정
                    Glide.with(context!!)
                        .load(response.body()?.imageUrl)
                        .into(festivalImage)
                }
            }

            override fun onFailure(call: Call<FestivalInfoRes>, t: Throwable) {
                println("실패!!!!!!!!!!!!!!!!!!!")
                t.printStackTrace()
            }
        })


        // 축제 코드 조회
        val hostpostApi = retrofit?.create(HostAPI::class.java)
        hostpostApi!!.getFestivalCode(token, "1").enqueue(object : Callback<FestivalCodeRes> {
            override fun onResponse(
                call: Call<FestivalCodeRes>,
                response: Response<FestivalCodeRes>
            ) {
                if (response.isSuccessful) {
                    println("성공!!!!!!!!!!!!!!!!!!!")
                    println(response.body())
                    Log.d("축제코드", "${response.body()}")
                    val code = view.findViewById<TextView>(R.id.festivalCode)
                    code.text = "축제코드 ${response.body()}"
                }
            }

            override fun onFailure(call: Call<FestivalCodeRes>, t: Throwable) {
                Log.d("축제코드", "실패!!!!!!!!!!!!!!!!!")
                t.printStackTrace()
            }
        })

    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}

