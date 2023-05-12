package com.example.festo.customer_ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R
import com.example.festo.booth_ui.BoothMainActivity
import com.example.festo.booth_ui.no_booth.NoBoothMainActivity
import com.example.festo.customer_ui.home.NotificationFragment
import com.example.festo.databinding.FragmentMypageBinding
import com.example.festo.host_ui.HostMainActivity
import com.example.festo.host_ui.no_festival.NoFeativalMainActivity


class MypageFragment : Fragment() {

    private var mBinding : FragmentMypageBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentMypageBinding.inflate(inflater, container, false)

        mBinding = binding
        mBinding!!.ivProfile2.setOnClickListener{
            // 등록된 부스가 있는 경우
//            val intent = Intent(getActivity(), BoothMainActivity::class.java)
//            startActivity(intent)

            // 등록된 부스가 없는 경우
            val intent = Intent(getActivity(), NoBoothMainActivity::class.java)
            startActivity(intent)
        }

        mBinding!!.ivProfile3.setOnClickListener{
            // 등록된 축제가 있는 경우
            val intent = Intent(getActivity(), HostMainActivity::class.java)
            startActivity(intent)

            // 등록된 축제가 없는 경우
//            val intent = Intent(getActivity(), NoFeativalMainActivity::class.java)
//            startActivity(intent)
        }

        // 알림으로 이동
        mBinding!!.notificationBtn.setOnClickListener{
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.layout_nav_bottom, NotificationFragment())
            transaction?.commit()
        }

        return  mBinding?.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}