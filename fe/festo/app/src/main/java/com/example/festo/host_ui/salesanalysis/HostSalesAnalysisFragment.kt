package com.example.festo.host_ui.salesanalysis

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
import com.example.festo.R.drawable
import com.example.festo.data.API.HostAPI
import com.example.festo.data.API.UserAPI
import com.example.festo.data.res.FestivalCodeRes
import com.example.festo.data.res.FestivalInfoRes
import com.example.festo.data.res.MyFestivalRes
import com.example.festo.databinding.FragmentHostSalesanalysisBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Locale

class BoothRankData(
    var image: Int? = null,
    var name: String? = null,
    var category : String? = null,
    var boothNum: String? = null,
    var totalOrder: Int? = null,
    var totalSales: Int? = null,
)
class HostSalesAnalysisFragment : Fragment() {
    private lateinit var listAdapter: BoothRankListAdapter
    private var mBinding : FragmentHostSalesanalysisBinding? = null
    private var retrofit = RetrofitClient.client
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentHostSalesanalysisBinding.inflate(inflater, container, false)

        mBinding = binding
        return  mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var BoothRankDataList : ArrayList <BoothRankData> = arrayListOf(
            BoothRankData(drawable.logo1,"까사꼬치", "고기", "C106", 33, 5000000),
            BoothRankData(drawable.logo2,"까사꼬치", "고기", "C106", 33, 4000000),
            BoothRankData(drawable.logo3,"까사꼬치", "고기", "C106", 33, 3000000),
            BoothRankData(drawable.logo2,"까사꼬치", "고기", "C106", 33, 2000000),
            BoothRankData(drawable.logo3,"까사꼬치", "고기", "C106", 33, 1000000),
            BoothRankData(drawable.logo2,"까사꼬치", "고기", "C106", 33, 1000000),
            BoothRankData(drawable.logo3,"까사꼬치", "고기", "C106", 33, 1000000),
        )
        listAdapter = BoothRankListAdapter(BoothRankDataList)
        mBinding?.boothRankListView?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mBinding?.boothRankListView?.adapter = listAdapter

        // 축제 상세정보 조회
        val sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val myValue = sharedPreferences.getString("myToken", "")
        val token = "$myValue"
        val hostApi = retrofit?.create(HostAPI::class.java)
        hostApi!!.getMyFestival(token).enqueue(object : Callback<List<MyFestivalRes>> {
            override fun onResponse(
                call: Call<List<MyFestivalRes>>,
                response: Response<List<MyFestivalRes>>
            ) {
                if (response.isSuccessful) {
                    println("성공!!!!!!!!!!!!!!!!!!!")
                    Log.d("축제이름만 가져오기", "${response.body()?.get(0)?.name}")
                    val festivalName = view.findViewById<TextView>(R.id.festivalName)

                    // 데이터 xml에 입력
                    festivalName.text = "${response.body()?.get(0)?.name} 매출분석"
                }
            }

            override fun onFailure(call: Call<List<MyFestivalRes>>, t: Throwable) {
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