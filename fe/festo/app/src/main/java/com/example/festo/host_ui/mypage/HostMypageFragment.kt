package com.example.festo.booth_ui.mypage



import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.festo.booth_ui.BoothMainActivity
import com.example.festo.customer_ui.home.HomeActivity
import com.example.festo.databinding.FragmentHostMypageBinding

class HostMypageFragment : Fragment() {
    private var mBinding : FragmentHostMypageBinding? = null
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
            val intent = Intent(getActivity(), BoothMainActivity::class.java)
            startActivity(intent)
        }
        return  mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}