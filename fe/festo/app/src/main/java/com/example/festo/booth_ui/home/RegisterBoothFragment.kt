package com.example.festo.booth_ui.home



import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.festo.customer_ui.home.HomeActivity
import com.example.festo.databinding.FragmentRegisterboothBinding

class RegisterBoothFragment : Fragment() {
    private var mBinding : FragmentRegisterboothBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentRegisterboothBinding.inflate(inflater, container, false)
        mBinding = binding

        binding.ivBoothImage.setOnClickListener{
            startActivityForResult(Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI), 0)
        }

        binding.tvStartTime.setOnClickListener {
            var calendar = Calendar.getInstance()
            var hour = calendar.get(Calendar.HOUR)
            var minute = calendar.get(Calendar.MINUTE)

            var listener = TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                binding.tvStartTime.text = "${i} : ${i2}"
            }

            var picker = TimePickerDialog( requireContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,listener, hour, minute, true ) // true하면 24시간 제
            picker.show()
        }
        binding.tvEndTime.setOnClickListener {
            var calendar = Calendar.getInstance()
            var hour = calendar.get(Calendar.HOUR)
            var minute = calendar.get(Calendar.MINUTE)

            var listener = TimePickerDialog.OnTimeSetListener { timePicker, i, i2 ->
                binding.tvEndTime.text = "${i} : ${i2}"
            }

            var picker = TimePickerDialog( requireContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,listener, hour, minute, true ) // true하면 24시간 제
            picker.show()
        }
        return  mBinding?.root
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(data!= null) mBinding?.ivBoothImage?.setImageURI(data?.data)
    }
}