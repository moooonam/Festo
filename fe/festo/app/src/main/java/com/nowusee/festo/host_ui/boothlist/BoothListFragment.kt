package com.nowusee.festo.host_ui.boothlist

import RetrofitClient
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nowusee.festo.data.API.HostAPI
import com.nowusee.festo.data.API.UserAPI
import com.nowusee.festo.data.res.BoothListRes
import com.nowusee.festo.data.res.MyFestivalRes
import com.nowusee.festo.databinding.FragmentHostBoothlistBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoothListFragment : Fragment() {
    private lateinit var listAdapter: BoothlistAdapter
    private var mBinding: FragmentHostBoothlistBinding? = null
    private var retrofit = RetrofitClient.client
    private var boothList = emptyList<BoothListRes>()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentHostBoothlistBinding.inflate(inflater, container, false)

        mBinding = binding
        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 부스 리스트 조회
        val postApi = retrofit?.create(UserAPI::class.java)
        val sharedPreferences =
            requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val myValue = sharedPreferences.getString("myToken", "")
        val token = "$myValue"
        val hostApi = retrofit?.create(HostAPI::class.java)
        hostApi!!.getMyFestival(token).enqueue(object : Callback<List<MyFestivalRes>> {
            override fun onResponse(
                call: Call<List<MyFestivalRes>>,
                response: Response<List<MyFestivalRes>>
            ) {
                if (response.isSuccessful) {
                    postApi!!.getBoothList(token, response.body()?.get(0)?.festivalId.toString())
                        .enqueue(object : Callback<List<BoothListRes>> {
                            override fun onResponse(
                                call: Call<List<BoothListRes>>,
                                response: Response<List<BoothListRes>>
                            ) {
                                if (response.isSuccessful) {
//                                    println("성공!!!!!!!!!!!!!!!!!!!")
//                                    println(response.body()?.size)
//                                    Log.d(" 테스트", "${response.body()}")
                                    boothList = response.body() ?: emptyList()
//                    부스 리스트 연결
                                    listAdapter = BoothlistAdapter(boothList)
                                    mBinding?.boothlistFragmentListView?.layoutManager =
                                        LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                                    mBinding?.boothlistFragmentListView?.adapter = listAdapter

                                }
                            }

                            override fun onFailure(call: Call<List<BoothListRes>>, t: Throwable) {
//                                println("실패!!!!!!!!!!!!!!!!!!!")
                                t.printStackTrace()
                            }
                        })

                }
            }

            override fun onFailure(call: Call<List<MyFestivalRes>>, t: Throwable) {
//                println("실패!!!!!!!!!!!!!!!!!!!")
                t.printStackTrace()
            }
        })
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}