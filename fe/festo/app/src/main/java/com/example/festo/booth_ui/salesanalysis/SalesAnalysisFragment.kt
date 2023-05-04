package com.example.festo.booth_ui.salesanalysis

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R
import com.example.festo.databinding.FragmentBoothSalesanalysisBinding
import com.example.festo.host_ui.boothlist.BoothListData
import com.example.festo.host_ui.boothlist.BoothlistAdapter

class NewMenuData(
    var image: Int? = null,
    var name: String? = null,
)
class SalesAnalysisFragment : Fragment() {
    private lateinit var listAdapter: NewMenuListAdapter
    private var mBinding : FragmentBoothSalesanalysisBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentBoothSalesanalysisBinding.inflate(inflater, container, false)

        mBinding = binding
        return  mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var NewMenuDataList : ArrayList <NewMenuData> = arrayListOf(
            NewMenuData(R.drawable.logo1,"까사꼬치"),
            NewMenuData(R.drawable.logo2,"까사꼬치"),
            NewMenuData(R.drawable.logo3,"까사꼬치")
        )
        listAdapter = NewMenuListAdapter(NewMenuDataList)
        mBinding?.newMenuListView?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mBinding?.newMenuListView?.adapter = listAdapter
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}