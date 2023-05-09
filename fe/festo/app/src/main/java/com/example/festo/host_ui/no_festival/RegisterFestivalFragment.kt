package com.example.festo.host_ui.no_festival


import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import com.example.festo.customer_ui.home.HomeActivity
import com.example.festo.data.req.RegiFestivalRequest
import com.example.festo.data.req.RegisterFestivalReq
import com.example.festo.databinding.FragmentRegisterFestivalBinding
import com.example.festo.host_ui.HostMainActivity
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener


class RegisterFestivalFragment : Fragment() {
    private var mBinding: FragmentRegisterFestivalBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentRegisterFestivalBinding.inflate(inflater, container, false)
        mBinding = binding
        // 축제 이미지 번경
        binding.ivFestivalImage.setOnClickListener {
            startActivityForResult(
                Intent(
                    Intent.ACTION_PICK,
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI
                ), 0
            )
        }
        var startDate: String? = null
        var endDate: String? = null

        // 날짜 설정
        binding.ivCalendar.setOnClickListener {
            val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("축제 기간을 골라주세요").build()
            dateRangePicker.show(childFragmentManager, "date_picker")
            dateRangePicker.addOnPositiveButtonClickListener(object :
                MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>> {
                override fun onPositiveButtonClick(selection: Pair<Long, Long>?) {
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = selection?.first ?: 0
                    startDate = SimpleDateFormat("yyyyMMdd").format(calendar.time).toString()
                    Log.d("start", startDate!!)

                    calendar.timeInMillis = selection?.second ?: 0
                    endDate = SimpleDateFormat("yyyyMMdd").format(calendar.time).toString()
                    Log.d("end", endDate!!)

                    binding.tvFestivalperiod.setText(dateRangePicker.headerText)

                }
            })
        }

        binding.tvFestivalperiod.setOnClickListener {
            val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("축제 기간을 골라주세요").build()
            dateRangePicker.show(childFragmentManager, "date_picker")
            dateRangePicker.addOnPositiveButtonClickListener(object :
                MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>> {
                override fun onPositiveButtonClick(selection: Pair<Long, Long>?) {
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = selection?.first ?: 0
                    startDate = SimpleDateFormat("yyyyMMdd").format(calendar.time).toString()
                    Log.d("start", startDate!!)
                    calendar.timeInMillis = selection?.second ?: 0
                    endDate = SimpleDateFormat("yyyyMMdd").format(calendar.time).toString()
                    Log.d("end", endDate!!)

                    binding.tvFestivalperiod.setText(dateRangePicker.headerText)

                }
            })
        }

        // 축제 등록 버튼 누르면
        binding.registerFestival.setOnClickListener {
            // 데이터 넣기
            Log.d("버튼은", "반응함")
            if (binding.etFestivalName.text.toString().isEmpty()) {
                Log.d("if", "들어옴")
                Toast.makeText(requireActivity(), "축제 이름을 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else if (binding.etFestivalDescription.text.toString().isEmpty()) {
                Toast.makeText(requireActivity(), "축제 설명을 입력해 주세요", Toast.LENGTH_SHORT).show()
            } else if (startDate.isNullOrEmpty() or endDate.isNullOrEmpty()) {
                Toast.makeText(requireActivity(), "축제 기간을 설정해 주세요", Toast.LENGTH_SHORT).show()
            } else if (binding.etFestivalLocation.text.toString().isEmpty()) {
                Toast.makeText(requireActivity(), "축제 장소를 입력해 주세요", Toast.LENGTH_SHORT).show()
            }

            else {

                val request = RegiFestivalRequest(
                    binding.etFestivalName.text.toString(),
                    binding.etFestivalLocation.text.toString(),
                    binding.etFestivalDescription.text.toString(),
                    startDate,
                    endDate
                )
                val data = RegisterFestivalReq(request, "이미지들어갈자리")
                Log.d("과연", "${data}")

                // 메인페이지 이동
                val intent = Intent(requireContext(), HostMainActivity::class.java)
                startActivity(intent)
            }
        }

        // 일반 사용자 마이페이지로 이동
        binding.goCustomer.setOnClickListener {
            val intent = Intent(requireContext(), HomeActivity::class.java)
            intent.putExtra("fragment", "MypageFragment")
            startActivity(intent)
        }


        return mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) mBinding?.ivFestivalImage?.setImageURI(data?.data)
    }
}