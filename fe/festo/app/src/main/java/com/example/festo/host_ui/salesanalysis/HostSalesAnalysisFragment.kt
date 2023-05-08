package com.example.festo.host_ui.salesanalysis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R.drawable
import com.example.festo.databinding.FragmentHostSalesanalysisBinding

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
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}