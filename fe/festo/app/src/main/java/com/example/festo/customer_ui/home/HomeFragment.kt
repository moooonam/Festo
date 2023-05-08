package com.example.festo.customer_ui.home

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.festo.R
import com.example.festo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var mBinding : FragmentHomeBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

       var binding = FragmentHomeBinding.inflate(inflater, container, false)
        mBinding = binding
     /*   val notificotionBtn = binding.notificationBtn
        notificotionBtn.setOnClickListener{
            showDialog()
        }*/
        return  mBinding?.root
    }

 /*   private fun showDialog() {
        val array = arrayOf("dog", "cat", "cow")
        AlertDialog.Builder(this)
            .setTitle("list")
            .setItems(array, object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface, which: Int) {
                    val currentItem = array[which]
                    Log.d("MyTag", "currentItem : $currentItem")
                }
            })
            .show()

    }*/

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}