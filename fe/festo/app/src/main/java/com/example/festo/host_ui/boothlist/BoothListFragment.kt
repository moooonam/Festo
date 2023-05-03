package com.example.festo.host_ui.boothlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R
import com.example.festo.databinding.FragmentHostBoothlistBinding

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
        var BoothListDataList : ArrayList <BoothListData> = arrayListOf(
            BoothListData(R.drawable.logo1,"까사꼬치","카테고리1", "설명1",87),
            BoothListData(R.drawable.logo2,"까사꼬치","카테고리2", "설명2",87),
            BoothListData(R.drawable.logo3,"까사꼬치","카테고리3", "설명3",87)
        )
        listAdapter = BoothlistAdapter(BoothListDataList)
        mBinding?.boothlistFragmentListView?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mBinding?.boothlistFragmentListView?.adapter = listAdapter
    }
    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}