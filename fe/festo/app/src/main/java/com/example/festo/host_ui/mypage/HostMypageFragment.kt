package com.example.festo.booth_ui.mypage



import RetrofitClient
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.festo.booth_ui.BoothMainActivity
import com.example.festo.booth_ui.no_booth.NoBoothMainActivity
import com.example.festo.customer_ui.home.HomeActivity
import com.example.festo.data.API.UserAPI
import com.example.festo.data.res.MyBoothListRes
import com.example.festo.databinding.FragmentHostMypageBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HostMypageFragment : Fragment() {
    private var mBinding : FragmentHostMypageBinding? = null
    private var retrofit = RetrofitClient.client
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentHostMypageBinding.inflate(inflater, container, false)

        mBinding = binding

        mBinding!!.ivProfile1.setOnClickListener{
            val intent = Intent(getActivity(), HomeActivity::class.java)
            startActivity(intent)
        }
        mBinding!!.ivProfile2.setOnClickListener{
//            val intent = Intent(getActivity(), BoothMainActivity::class.java)
//            startActivity(intent)

            val intent = Intent(getActivity(), NoBoothMainActivity::class.java)
            startActivity(intent)
        }
        return  mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}