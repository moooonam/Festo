package com.example.festo.booth_ui.salesanalysis

import RetrofitClient
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R
import com.example.festo.data.API.UserAPI
import com.example.festo.data.res.BoothDailySales
import com.example.festo.data.res.BoothDetailRes
import com.example.festo.databinding.FragmentBoothSalesanalysisBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
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
    private lateinit var listAdapter: MenuRankListAdapter
    private var mBinding: FragmentBoothSalesanalysisBinding? = null
    private var boothDailySalesList: ArrayList<BoothDailySales> = ArrayList()
    private lateinit var chart: BarChart
    private val colorList = ArrayList<Int>()


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

        fun basicSetting() {
            chart.apply {
                description.isEnabled = false
                setMaxVisibleValueCount(5) // 최대로 보여지는 데이터의 수
                setPinchZoom(false)
                setDrawBarShadow(false)
                setDrawGridBackground(false)
                setDrawBorders(false)
                legend.isEnabled = false
                setTouchEnabled(true)
                isDoubleTapToZoomEnabled = false
                animateY(3000) // 애니메이션 효과 적용 시간
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
                // y축 레이블 표시
                isEnabled = false
                setDrawLabels(false)
                val maxValue = boothDailySalesList.maxByOrNull { it.amount }?.amount?.toFloat() ?: 0f
                axisMaximum = maxValue + 1000000F
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

        fun createBarChart() {
            val values = ArrayList<BarEntry>()
            val type = ArrayList<String>()
            val set: BarDataSet

            // 기본값 세팅
            values.add(BarEntry(1.0f, 320.0f))
            values.add(BarEntry(2.0f, 430.0f))
            values.add(BarEntry(3.0f, 440.0f))
            values.add(BarEntry(4.0f, 300.0f))
            values.add(BarEntry(5.0f, 520.0f))

            // 리스트 값 돌면서 x값은 1만큼 증가시켜 막대마다 거리를 두고, y값은 리스트의 amount값을 가져와서 표현
            val lastValues = boothDailySalesList.takeLast(values.size).mapIndexed { index, boothDailySales ->
                BarEntry((index + 1).toFloat(), boothDailySales.amount.toFloat())
            }
            values.addAll(lastValues)

            type.add(" ")
            boothDailySalesList.forEach { boothDailySales ->
                val date = boothDailySales.date
                val day = date.substring(date.lastIndexOf("-") + 1) // 일(day) 추출
                type.add(day + "일")
            }

            colorList.clear()
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

        basicSetting()
        setXAxis()
        setLeftYAxis()
        setRightYAxis()
        createBarChart()

        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 부스 아이디
        val sharedPreferences = requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val boothId = sharedPreferences.getString("boothId", "")

        var menuRankDataList: ArrayList<MenuRankData> = arrayListOf(
            MenuRankData(R.drawable.logo1, "데리야끼꼬치", 50000000),
            MenuRankData(R.drawable.logo2, "불닭꼬치", 20000000),
            MenuRankData(R.drawable.logo3, "갈릭꼬치", 10000000)
        )
        listAdapter = MenuRankListAdapter(menuRankDataList)
        mBinding?.newMenuListView?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mBinding?.newMenuListView?.adapter = listAdapter

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
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {
        if (e != null) {
            // 막대의 인덱스는 1부터 시작하므로 1을 빼줍니다.
            val selectedIndex = e.x.toInt() - 1

            if (selectedIndex >= 0 && selectedIndex < boothDailySalesList.size) {
                val selectedData = boothDailySalesList[selectedIndex]

//                // 선택한 막대의 색상을 변경하기 위해 데이터셋을 가져옵니다.
//                val dataSet = chart.data.getDataSetByIndex(0) as BarDataSet
//
//
//                // 모든 막대의 색상을 원래 색으로 초기화합니다.
//                dataSet.colors = colorList.toMutableList()
//
//                // 선택한 막대의 색상을 변경합니다.
//                if (selectedIndex < colorList.size) {
//                    val modifiedColors = colorList.toMutableList()
//                    modifiedColors[selectedIndex] = Color.GREEN
//                    dataSet.colors = modifiedColors
//                }
//
//                // 차트를 갱신합니다.
//                chart.invalidate()

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


