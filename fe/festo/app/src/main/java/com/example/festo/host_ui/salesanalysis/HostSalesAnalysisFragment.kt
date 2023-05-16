package com.example.festo.host_ui.salesanalysis

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.festo.R
import com.example.festo.data.API.HostAPI
import com.example.festo.data.DataRetrofitClient
import com.example.festo.data.res.BoothData
import com.example.festo.data.res.FestivalAnalysisRes
import com.example.festo.data.res.MyFestivalRes
import com.example.festo.databinding.FragmentHostSalesanalysisBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HostSalesAnalysisFragment : Fragment() {
    private lateinit var listAdapter: BoothRankListAdapter
    private var mBinding : FragmentHostSalesanalysisBinding? = null
    private var retrofit = RetrofitClient.client
    private var dataRetrofit = DataRetrofitClient.client
    private var recommendList = emptyList<BoothData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentHostSalesanalysisBinding.inflate(inflater, container, false)
        mBinding = binding

        // 차트 생성 코드
        val chart = binding.barChart

        fun basicSetting() {
            chart.apply {
                description.isEnabled = false
                setMaxVisibleValueCount(5) // 최대로 보여지는 데이터의 수
                setPinchZoom(false)
                setDrawBarShadow(false)
                setDrawGridBackground(false)
                setDrawBorders(false)
                legend.isEnabled = false
                setTouchEnabled(false)
                isDoubleTapToZoomEnabled = false
                animateY(5000) // 애니메이션 효과 적용 시간
            }
        }

        fun setxAxis() {
            val xAxis = chart.xAxis
            xAxis.apply {
                setDrawGridLines(false)
                isEnabled = true
                position = XAxis.XAxisPosition.BOTTOM
                disableGridDashedLine()
                setDrawAxisLine(false)
            }
        }

        fun setLeftXaxis() {
            val leftXaxis = chart.axisLeft
            leftXaxis.apply {
                setDrawGridLines(false)
                setDrawAxisLine(false)
                isEnabled = false
                setDrawLabels(false)
                axisMaximum = 600f
            }
        }

        fun setRightXaxis() {
            val rightXaxis = chart.axisRight
            rightXaxis.apply {
                setDrawGridLines(false)
                setDrawAxisLine(false)
                isEnabled = false
                setDrawLabels(false)
            }
        }

        fun createBarChart() {
            val values = ArrayList<BarEntry>()
            val type = ArrayList<String>()
            val colorList = ArrayList<Int>()
            val set : BarDataSet

            values.add(BarEntry(1.0f, 320.0f))
            values.add(BarEntry(2.0f, 430.0f))
            values.add(BarEntry(3.0f, 440.0f))
            values.add(BarEntry(4.0f, 300.0f))
            values.add(BarEntry(5.0f, 520.0f))

            type.add(" ")
            type.add("26일")
            type.add("27일")
            type.add("28일")
            type.add("29일")
            type.add("30일")

            colorList.add(Color.parseColor("#F24E1E"))

            if (chart.data != null && chart.data.dataSetCount > 1) {
                val chartData = chart.data
                set = chartData?.getDataSetByIndex(0) as BarDataSet
                set.values = values
                chartData.notifyDataChanged()
                chart.notifyDataSetChanged()
            } else {
                set = BarDataSet(values, " ")
                set.colors = colorList
                set.setDrawValues(true)

                val dataSets = ArrayList<IBarDataSet>()
                dataSets.add(set)

                val data = BarData(dataSets)
                chart.data = data
                chart.setVisibleXRange(1.0f,5.0f)
                chart.setFitBars(true)

                val xAxis = chart.xAxis
                xAxis.apply {
                    granularity = 1f
                    isGranularityEnabled = true
                    valueFormatter = IndexAxisValueFormatter(type)
                }
                chart.invalidate()
            }
        }

        basicSetting()
        setxAxis()
        setLeftXaxis()
        setRightXaxis()
        createBarChart()

        return  mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // data 와 api에 보낼 토큰 선언
        val sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val myValue = sharedPreferences.getString("myToken", "")
        val token = "$myValue"
        // hostApi 선언
        val hostApi = retrofit?.create(HostAPI::class.java)

        hostApi!!.getMyFestival(token).enqueue(object : Callback<List<MyFestivalRes>> {
            override fun onResponse(
                call: Call<List<MyFestivalRes>>,
                response: Response<List<MyFestivalRes>>
            ) {
                if (response.isSuccessful) {
                    val dataHostApi = dataRetrofit?.create(HostAPI::class.java)
                    dataHostApi!!.getFestivalSalesAnalysis(token, response.body()?.get(0)?.festivalId.toString()).enqueue(object : Callback<FestivalAnalysisRes> {
                        @SuppressLint("NotifyDataSetChanged")
                        override fun onResponse(
                            call: Call<FestivalAnalysisRes>,
                            response: Response<FestivalAnalysisRes>
                        ) {
                            if (response.isSuccessful) {
                                Log.i("창민추천", response.body()?.booth_data?.get(1)?.booth_name.toString())
                                Log.i("창민추천", response.body()?.booth_data?.get(0)?.image_url.toString())
                                recommendList = response.body()?.booth_data ?: emptyList()
                                listAdapter.updateList(recommendList)

                                // 1등 ~ 3등 이름
                                val rank1Name = view.findViewById<TextView>(R.id.rank_1_Name)
                                val rank2Name = view.findViewById<TextView>(R.id.rank_2_Name)
                                val rank3Name = view.findViewById<TextView>(R.id.rank_3_Name)
                                // 1등 ~ 3등 부스 이미지
                                val rank1Image = view.findViewById<ImageView>(R.id.rank_1_Image)
                                val rank2Image = view.findViewById<ImageView>(R.id.rank_2_Image)
                                val rank3Image = view.findViewById<ImageView>(R.id.rank_3_Image)

                                // 리스폰스 받은 부스가 3개 이하 일 때 수에 맞게 출력
                                if (recommendList.size >= 1) {
                                    rank1Name.text = recommendList[0].booth_name
                                    Glide.with(requireContext())
                                        .load(recommendList[0].image_url)
                                        .into(rank1Image)
                                } else {
                                    val placeholderImage = ContextCompat.getDrawable(requireContext(),R.mipmap.ic_launcher)
                                    rank1Image.setImageDrawable(placeholderImage)
                                }

                                if (recommendList.size >= 2) {
                                    rank2Name.text = recommendList[1].booth_name
                                    Glide.with(requireContext())
                                        .load(recommendList[1].image_url)
                                        .into(rank2Image)
                                } else {
                                    val placeholderImage = ContextCompat.getDrawable(requireContext(),R.mipmap.ic_launcher)
                                    rank1Image.setImageDrawable(placeholderImage)
                                }

                                if (recommendList.size >= 3) {
                                    rank3Name.text = recommendList[2].booth_name
                                    Glide.with(requireContext())
                                        .load(recommendList[2].image_url)
                                        .into(rank3Image)
                                } else {
                                    val placeholderImage = ContextCompat.getDrawable(requireContext(),R.mipmap.ic_launcher)
                                    rank1Image.setImageDrawable(placeholderImage)
                                }
                            }
                        }
                        override fun onFailure(call: Call<FestivalAnalysisRes>, t: Throwable) {
                            TODO("Not yet implemented")
                        }
                    })
                }
            }
            override fun onFailure(call: Call<List<MyFestivalRes>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })

        listAdapter = BoothRankListAdapter(recommendList)
        mBinding?.boothRankListView?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mBinding?.boothRankListView?.adapter = listAdapter


        // 축제 상세정보 조회
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