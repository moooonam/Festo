package com.example.festo.booth_ui.salesanalysis

import RetrofitClient
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.media.Image
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
import com.example.festo.data.API.BoothAPI
import com.example.festo.data.API.UserAPI
import com.example.festo.data.DataRetrofitClient
import com.example.festo.data.res.BoothAnalysisRes
import com.example.festo.data.res.BoothDailySales
import com.example.festo.data.res.BoothDetailRes
import com.example.festo.data.res.MenuAnalysis
import com.example.festo.databinding.FragmentBoothSalesanalysisBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MenuRankData(
    var image: Int? = null,
    var name: String? = null,
    var totalSales: Int? = null,
)

class SalesAnalysisFragment : Fragment(), OnChartValueSelectedListener {
    private var retrofit = RetrofitClient.client
    private var dataRetrofit = DataRetrofitClient.client
    private lateinit var listAdapter: MenuRankListAdapter
    private var mBinding: FragmentBoothSalesanalysisBinding? = null
    private var boothDailySalesList: ArrayList<BoothDailySales> = ArrayList()
    private lateinit var chart: BarChart
    private val colorList = ArrayList<Int>()
    private var menuRankList = emptyList<MenuAnalysis>()
    private var boothDailyList = emptyList<BoothDailySales>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentBoothSalesanalysisBinding.inflate(inflater, container, false)
        mBinding = binding

        boothDailySalesList.add(BoothDailySales("2023-09-23", 1, 5800000))
        boothDailySalesList.add(BoothDailySales("2023-09-20", 2, 5000000))
        boothDailySalesList.add(BoothDailySales("2023-09-17", 3, 2000000))
        boothDailySalesList.add(BoothDailySales("2023-09-20", 4, 5000000))
        boothDailySalesList.add(BoothDailySales("2023-09-17", 5, 2000000))

        // 차트 생성 코드
        chart = binding.barChart

//        createBarChart()

        return mBinding?.root
    }

    fun createBarChart() {
        val values = ArrayList<BarEntry>()
        val type = ArrayList<String>()
        val set: BarDataSet


        // 차트에 amount 데이터 입력
        val lastValues = boothDailyList.takeLast(boothDailyList.size).mapIndexed { index, boothDailySales ->
            BarEntry((index + 1).toFloat(), boothDailySales.amount.toFloat())
        }
        values.addAll(lastValues)

        // 차트에 날짜 데이터 입력
        type.add(" ")
        boothDailyList.forEach { boothDailySales ->
            val date = boothDailySales.date
            val day = date.substring(date.lastIndexOf("-") + 1) // 일(day) 추출
            type.add(day + "일")
        }

        basicSetting()
        setXAxis()
        setLeftYAxis()
        setRightYAxis()

//        colorList.clear()
//        colorList.add(Color.parseColor("#F24E1E"))

        if (chart.data != null && chart.data.dataSetCount > 1) {
            val chartData = chart.data
            set = chartData?.getDataSetByIndex(0) as BarDataSet
            set.values = values
            chartData.notifyDataChanged()
            chart.notifyDataSetChanged()
        } else {
            set = BarDataSet(values, " ")
            set.color = Color.parseColor("#F24E1E")
            set.setDrawValues(true)

            val dataSets = ArrayList<IBarDataSet>()
            dataSets.add(set)

            val data = BarData(dataSets)
            chart.data = data
            chart.setVisibleXRange(1.0f, 5.0f)
            chart.setFitBars(true)

            val xAxis = chart.xAxis
            xAxis.apply {
                granularity = 1f
                isGranularityEnabled = true
                valueFormatter = IndexAxisValueFormatter(type)
            }
            chart.invalidate()
        }

        chart.setOnChartValueSelectedListener(this)
    }

    fun basicSetting() {
        chart.apply {
            description.isEnabled = false
            setMaxVisibleValueCount(boothDailyList.size ) // 최대로 보여지는 데이터의 수
            setPinchZoom(false)
            setDrawBarShadow(false)
            setDrawGridBackground(false)
            setDrawBorders(false)
            legend.isEnabled = false
            setTouchEnabled(true)
            isDoubleTapToZoomEnabled = false
//            animateY(3000) // 애니메이션 효과 적용 시간
        }
    }

    fun setXAxis() {
        val xAxis = chart.xAxis
        xAxis.apply {
            setDrawGridLines(false)
            isEnabled = true
            position = XAxis.XAxisPosition.BOTTOM
            disableGridDashedLine()
            setDrawAxisLine(false)
        }
    }

    fun setLeftYAxis() {
        val leftYAxis = chart.axisLeft
        leftYAxis.apply {
            setDrawGridLines(false)
            setDrawAxisLine(false)
            isEnabled = true
            setDrawLabels(true)
            axisMaximum = 200000f
        }
    }


    fun setRightYAxis() {
        val rightYAxis = chart.axisRight
        rightYAxis.apply {
            setDrawGridLines(false)
            setDrawAxisLine(false)
            isEnabled = false
            setDrawLabels(false)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 부스 아이디
        val sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val boothId = sharedPreferences.getString("boothId", "")

        // 부스 상세정보 retrofit.
        val postApi = retrofit?.create(UserAPI::class.java)
        val myValue = sharedPreferences.getString("myToken", "")
        val token = "$myValue"
        postApi!!.getBoothDetail(token, boothId.toString()).enqueue(object :
            Callback<BoothDetailRes> {
            override fun onResponse(
                call: Call<BoothDetailRes>,
                response: Response<BoothDetailRes>
            ) {
                if (response.isSuccessful) {
                    println("성공!!!!!!!!!!!!!!!!!!!")
                    println(response.body())
                    Log.d("부스이름만 가져오기", "${response.body()?.name}")
                    val boothName = view.findViewById<TextView>(R.id.boothName)

                    // 데이터 xml에 입력
                    boothName.text = "${response.body()?.name} 매출분석"

                }
            }

            override fun onFailure(call: Call<BoothDetailRes>, t: Throwable) {
                println("실패!!!!!!!!!!!!!!!!!!!")
                t.printStackTrace()
            }
        })

        // 부스 매출 데이터 불러오기
        val dataPostApi = dataRetrofit?.create(BoothAPI::class.java)
        dataPostApi!!.getBoothSalesAnalysis(token, boothId.toString()).enqueue(object :
            Callback<BoothAnalysisRes> {
            override fun onResponse(
                call: Call<BoothAnalysisRes>,
                response: Response<BoothAnalysisRes>
            ) {
                if (response.isSuccessful) {
                    println("성공!!!!!!!!!!!!!!!!!!!")
                    println(response.body())
                    Log.d("부스매출분석 데이터 불러오기 성공", "${response.body()}")
                    menuRankList = response.body()?.menu ?: emptyList()
                    boothDailyList = response.body()?.daily_sales ?: emptyList()

                    val textView = view.findViewById<TextView>(R.id.nodata)

                    if (boothDailyList.size < 2) {
                        // boothDailyList의 개수가 2개 미만인 경우에는 TextView를 추가하고 Bar 차트를 숨깁니다.
                        textView.visibility = View.VISIBLE
                        chart.visibility = View.GONE
                    } else {
                        textView.visibility = View.GONE
                        chart.visibility = View.VISIBLE

                        createBarChart()
                    }

                    // 메뉴 리스트 연결
                    listAdapter = MenuRankListAdapter(menuRankList)
                    mBinding?.newMenuListView?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                    mBinding?.newMenuListView?.adapter = listAdapter

                    // 인기메뉴 연결
                    var topName1 = view.findViewById<TextView>(R.id.topMenuName1)
                    var topName2 = view.findViewById<TextView>(R.id.topMenuName2)
                    var topName3 = view.findViewById<TextView>(R.id.topMenuName3)
                    var topImg1 = view.findViewById<ImageView>(R.id.topMenuImg1)
                    var topImg2 = view.findViewById<ImageView>(R.id.topMenuImg2)
                    var topImg3 = view.findViewById<ImageView>(R.id.topMenuImg3)

                    if (menuRankList.size >= 3) {
                        topName1.text = menuRankList[0].name
                        topName2.text = menuRankList[1].name
                        topName3.text = menuRankList[2].name
                        Glide.with(requireContext())
                            .load(menuRankList[0].image_url)
                            .into(topImg1)
                        Glide.with(requireContext())
                            .load(menuRankList[1].image_url)
                            .into(topImg2)
                        Glide.with(requireContext())
                            .load(menuRankList[2].image_url)
                            .into(topImg3)
                    } else if (menuRankList.size == 2) {
                        topName1.text = menuRankList[0].name
                        topName2.text = menuRankList[1].name
                        topName3.text = null
                        Glide.with(requireContext())
                            .load(menuRankList[0].image_url)
                            .into(topImg1)
                        Glide.with(requireContext())
                            .load(menuRankList[1].image_url)
                            .into(topImg2)
                        val placeholderImage = ContextCompat.getDrawable(requireContext(),R.mipmap.ic_launcher)
                        topImg3.setImageDrawable(placeholderImage)
                    } else if (menuRankList.size == 1) {
                        topName1.text = menuRankList[0].name
                        topName2.text = null
                        topName3.text = null
                        Glide.with(requireContext())
                            .load(menuRankList[0].image_url)
                            .into(topImg1)
                        val placeholderImage = ContextCompat.getDrawable(requireContext(),R.mipmap.ic_launcher)
                        topImg2.setImageDrawable(placeholderImage)
                        topImg3.setImageDrawable(placeholderImage)
                    } else {
                        topName1.text = null
                        topName2.text = null
                        topName3.text = null
                        val placeholderImage = ContextCompat.getDrawable(requireContext(),R.mipmap.ic_launcher)
                        topImg1.setImageDrawable(placeholderImage)
                        topImg2.setImageDrawable(placeholderImage)
                        topImg3.setImageDrawable(placeholderImage)
                    }


                } else {
                    Log.d("부스매출분석 실패다!!!", "${response.body()}")
                    println(boothId)

                }
            }

            override fun onFailure(call: Call<BoothAnalysisRes>, t: Throwable) {
                println("부스매출분석 실패!!!!!!!!!!!!!!!!!!!")
                t.printStackTrace()
            }
        })
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        if (e != null) {
            // 막대의 인덱스는 1부터 시작하므로 1을 빼줍니다.
            val selectedIndex = e.x.toInt() - 1

            if (selectedIndex >= 0 && selectedIndex < boothDailyList.size) {
                val selectedData = boothDailyList[selectedIndex]

                // 다이얼로그에 선택된 데이터를 표시하는 로직을 작성하면 됩니다.
                val dialog = AlertDialog.Builder(requireContext(), R.style.CustomDialogTheme)
                    .setTitle("매출 상세정보")
                    .setMessage("날짜: ${selectedData.date}\n누적 주문건수: ${selectedData.count}\n누적 매출: ${selectedData.amount}")
                    .setPositiveButton("OK", null)
                    .create()
                dialog.show()
            }
        }
    }




    override fun onNothingSelected() {
        // 아무 막대도 선택하지 않았을 때의 동작을 정의
    }
}


