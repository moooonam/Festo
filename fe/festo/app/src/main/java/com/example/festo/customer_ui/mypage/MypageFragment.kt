package com.example.festo.customer_ui.mypage

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R
import com.example.festo.booth_ui.BoothMainActivity
import com.example.festo.booth_ui.no_booth.NoBoothMainActivity
import com.example.festo.booth_ui.orderlist.BoothOrderListAdapter
import com.example.festo.customer_ui.home.NotificationFragment
import com.example.festo.data.API.BoothAPI
import com.example.festo.data.API.UserAPI
import com.example.festo.data.res.BoothOrderListRes
import com.example.festo.data.res.IsHaveFestivalRes
import com.example.festo.databinding.FragmentMypageBinding
import com.example.festo.host_ui.HostMainActivity
import com.example.festo.host_ui.no_festival.NoFeativalMainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MypageFragment : Fragment() {
    private var retrofit = RetrofitClient.client
    private var mBinding: FragmentMypageBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentMypageBinding.inflate(inflater, container, false)

        mBinding = binding
        mBinding!!.ivProfile2.setOnClickListener {
            // 등록된 부스가 있는 경우
//            val intent = Intent(getActivity(), BoothMainActivity::class.java)
//            startActivity(intent)

            // 등록된 부스가 없는 경우
            val intent = Intent(getActivity(), NoBoothMainActivity::class.java)
            startActivity(intent)
        }

        mBinding!!.ivProfile3.setOnClickListener {
            fun getIsHave() {

                val sharedPreferences =
                    requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                val myToken = sharedPreferences.getString("myToken", "")
                val memberId = sharedPreferences.getString("memberId", "")
                val token = "$myToken"
                val myId = "$memberId"
                Log.d("아이디", "${myId}")
                val postApi = retrofit?.create(UserAPI::class.java)
                postApi!!.getIsHaveFestival(token, myId).enqueue(object :
                    Callback<IsHaveFestivalRes> {
                    override fun onResponse(
                        call: Call<IsHaveFestivalRes>,
                        response: Response<IsHaveFestivalRes>
                    ) {
                        if (response.isSuccessful) {
                            val isFestival = response.body()?.open
                            Log.d(" 있나없나", "${response},  ${response.body()?.open}")
                            if (isFestival!!) {
                                val intent =
                                    Intent(getActivity(), NoFeativalMainActivity::class.java)
                                startActivity(intent)
                            } else {
                                val intent = Intent(getActivity(), HostMainActivity::class.java)
                                startActivity(intent)
                            }
                        }
                    }

                    override fun onFailure(call: Call<IsHaveFestivalRes>, t: Throwable) {
                        Log.d(" 부스 주문내역 실패", "응")
                        t.printStackTrace()
                    }
                })
            }
            getIsHave()

        }

        // 알림으로 이동
        mBinding!!.notificationBtn.setOnClickListener {
            val transaction = fragmentManager?.beginTransaction()
            transaction?.replace(R.id.layout_nav_bottom, NotificationFragment())
            transaction?.commit()
        }

        return mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}