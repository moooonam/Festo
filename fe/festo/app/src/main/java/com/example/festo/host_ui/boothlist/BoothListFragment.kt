package com.example.festo.host_ui.boothlist

import RetrofitClient
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.data.API.UserAPI
import com.example.festo.data.res.BoothListRes
import com.example.festo.databinding.FragmentHostBoothlistBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class BoothListData(
    var image: Int? = null,
    var name: String? = null,
    var category: String? = null,
    var explanation: String? = null,
    var totalOrder: Int? = null,
)

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
//        var BoothListDataList : ArrayList <BoothListData> = arrayListOf(
//            BoothListData(R.drawable.logo1,"까사꼬치","카테고리1", "설명1",87),
//            BoothListData(R.drawable.logo2,"까사꼬치","카테고리2", "설명2",87),
//            BoothListData(R.drawable.logo3,"까사꼬치","카테고리3", "설명3",87)
//        )
//        listAdapter = BoothlistAdapter(BoothListDataList)
//        mBinding?.boothlistFragmentListView?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
//        mBinding?.boothlistFragmentListView?.adapter = listAdapter

        // 부스 리스트 조회
        val postApi = retrofit?.create(UserAPI::class.java)
        val sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val myValue = sharedPreferences.getString("myToken", "")
        val token = "$myValue"
        postApi!!.getBoothList(token, "1").enqueue(object : Callback<List<BoothListRes>> {
            override fun onResponse(
                call: Call<List<BoothListRes>>,
                response: Response<List<BoothListRes>>
            ) {
                if (response.isSuccessful) {
                    println("성공!!!!!!!!!!!!!!!!!!!")
                    println(response.body()?.size)
                    Log.d(" 테스트", "${response.body()}")
                    boothList = response.body() ?: emptyList()
//                    부스 리스트 연결
                    listAdapter = BoothlistAdapter(boothList)
                    mBinding?.boothlistFragmentListView?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                    mBinding?.boothlistFragmentListView?.adapter = listAdapter

                }
            }

            override fun onFailure(call: Call<List<BoothListRes>>, t: Throwable) {
                println("실패!!!!!!!!!!!!!!!!!!!")
                t.printStackTrace()
            }
        })

    }
    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}