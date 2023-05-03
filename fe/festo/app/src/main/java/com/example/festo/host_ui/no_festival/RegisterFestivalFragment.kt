package com.example.festo.host_ui.no_festival



import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.core.util.Pair
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.example.festo.R
import com.example.festo.customer_ui.home.HomeActivity
import com.example.festo.databinding.FragmentRegisterFestivalBinding
import com.example.festo.host_ui.HostMainActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener


class RegisterFestivalFragment : Fragment() {
    private var mBinding : FragmentRegisterFestivalBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentRegisterFestivalBinding.inflate(inflater, container, false)
        mBinding = binding
        // 축제 이미지 번경
        binding.ivFestivalImage.setOnClickListener{
            startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), 0)
        }

        // 날짜 설정
        binding.ivCalendar.setOnClickListener{
            val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("축제 기간을 골라주세요").build()
            dateRangePicker.show(childFragmentManager, "date_picker")
            dateRangePicker.addOnPositiveButtonClickListener(object :
                MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>> {
                override fun onPositiveButtonClick(selection: Pair<Long, Long>?) {
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = selection?.first ?: 0
                    var startDate = SimpleDateFormat("yyyyMMdd").format(calendar.time).toString()
                    Log.d("start", startDate)

                    calendar.timeInMillis = selection?.second ?: 0
                    var endDate = SimpleDateFormat("yyyyMMdd").format(calendar.time).toString()
                    Log.d("end", endDate)

                    binding.tvFestivalperiod.setText(dateRangePicker.headerText)

                }
            })
        }
        binding.tvFestivalperiod.setOnClickListener{
            val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("축제 기간을 골라주세요").build()
            dateRangePicker.show(childFragmentManager, "date_picker")
            dateRangePicker.addOnPositiveButtonClickListener(object :
                MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>> {
                override fun onPositiveButtonClick(selection: Pair<Long, Long>?) {
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = selection?.first ?: 0
                    var startDate = SimpleDateFormat("yyyyMMdd").format(calendar.time).toString()
                    Log.d("start", startDate)
                    calendar.timeInMillis = selection?.second ?: 0
                    var endDate = SimpleDateFormat("yyyyMMdd").format(calendar.time).toString()
                    Log.d("end", endDate)

                    binding.tvFestivalperiod.setText(dateRangePicker.headerText)

                }
            })
        }

        // 축제 등록 버튼 누르면 메인 페이지로 이동
        binding.registerFestival.setOnClickListener{
            val intent = Intent(requireContext(), HostMainActivity::class.java)
            startActivity(intent)
        }

        // 일반 사용자 마이페이지로 이동
        binding.goCustomer.setOnClickListener {
            val intent = Intent(requireContext(), HomeActivity::class.java)
            intent.putExtra("fragment", "MypageFragment")
            startActivity(intent)
        }

        return  mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data!= null) mBinding?.ivFestivalImage?.setImageURI(data?.data)
    }
}