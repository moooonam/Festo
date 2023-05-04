package com.example.festo.booth_ui.home



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R
import com.example.festo.databinding.FragmentBoothHomeBinding
import com.example.festo.host_ui.boothlist.BoothlistAdapter

class MenuListData(
    var image: Int? = null,
    var name: String? = null,
    var price: Int? = null,
)
class BoothHomeFragment : Fragment() {
    private lateinit var listAdapter: MenuListAdapter
    private var mBinding : FragmentBoothHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentBoothHomeBinding.inflate(inflater, container, false)
        mBinding = binding

        return  mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var MenuListDataList : ArrayList <MenuListData> = arrayListOf(
            MenuListData(R.drawable.logo1,"까사꼬치",87),
            MenuListData(R.drawable.logo2,"까사꼬치",87),
            MenuListData(R.drawable.logo3,"까사꼬치",87)
        )
        listAdapter = MenuListAdapter(MenuListDataList)
        mBinding?.menulistFragmentListView?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mBinding?.menulistFragmentListView?.adapter = listAdapter
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}