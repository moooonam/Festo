package com.nowusee.festo.customer_ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nowusee.festo.data.API.UserAPI
import com.nowusee.festo.data.res.UserNotificationListRes
import com.nowusee.festo.databinding.FragmentNotificationBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NotificationData(
    var state: String? = null,
    var booth: String? = null,
    var date: String? = null,
)

class NotificationFragment : Fragment() {
    private var notificaionData = emptyList<UserNotificationListRes>()
    private lateinit var listAdapter: NotificationAdapter
    private var retrofit = RetrofitClient.client
    private var mBinding: FragmentNotificationBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentNotificationBinding.inflate(inflater, container, false)
        mBinding = binding
        return mBinding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fun getNotificationList() {
            Log.d(" 실행타이밍", "지금")
//            val sharedPreferences =
//                requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val sharedPreferences =
                requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
            val myValue = sharedPreferences.getString("myToken", "")
            val token = "$myValue"
            val postApi = retrofit?.create(UserAPI::class.java)
            postApi!!.getUserNotificationData(token).enqueue(object :
                Callback<List<UserNotificationListRes>> {
                override fun onResponse(
                    call: Call<List<UserNotificationListRes>>,
                    response: Response<List<UserNotificationListRes>>
                ) {
                    if (response.isSuccessful) {
                        Log.d(" 알림목록불러오기", "${response},  ${response.body()}")
                        notificaionData = response.body()!!
                        listAdapter = NotificationAdapter(notificaionData as MutableList<UserNotificationListRes>)
                        mBinding?.notificationListView?.layoutManager =
                            LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                        mBinding?.notificationListView?.adapter = listAdapter
                    }
                }

                override fun onFailure(call: Call<List<UserNotificationListRes>>, t: Throwable) {
                    Log.d(" 알림목록불러오기", "응")
                    t.printStackTrace()
                }
            })
        }
            getNotificationList()
            var NotifictionDataList: ArrayList<NotificationData> = arrayListOf(
                NotificationData("첫번째데이터1", "두번째데이터1", "2023년4월26일"),
                NotificationData("첫번째데이터2", "두번째데이터2", "2023년4월26일"),
                NotificationData("첫번째데이터3", "두번째데이터3", "2023년4월26일"),
                NotificationData("첫번째데이터4", "두번째데이터4", "2023년4월26일"),
                NotificationData("첫번째데이터5", "두번째데이터5", "2023년4월26일"),
            )
//        var list : ArrayList<NotificationData> = requireActivity().intent!!.extras!!.get("NotifictionDataList") as ArrayList<NotificationData>

//            listAdapter = NotificationAdapter(NotifictionDataList)
//            mBinding?.notificationListView?.layoutManager =
//                LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
//            mBinding?.notificationListView?.adapter = listAdapter

    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}
