package com.example.festo.booth_ui.home



import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R
import com.example.festo.data.req.RegiMenuReq
import com.example.festo.data.req.RegisterFestivalReq
import com.example.festo.data.req.RegisterMenuReq
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
    private var alertDialog: AlertDialog? = null
    private val galleryLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedImageUri = data?.data
                val addImgBtn = alertDialog?.findViewById<ImageView>(R.id.menu_img)
                addImgBtn?.setImageURI(selectedImageUri)
            }
        }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var binding = FragmentBoothHomeBinding.inflate(inflater, container, false)
        mBinding = binding
        mBinding!!.btnAddmenu.setOnClickListener {
            showAddMenuDialog()
        }
        return  mBinding?.root
    }
    private fun showAddMenuDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_addmenu, null)

        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("메뉴 추가")
            .setPositiveButton("추가하기") { dialogInterface, _ ->
                val photo = dialogView.findViewById<ImageView>(R.id.menu_img)
                val meueName = dialogView.findViewById<EditText>(R.id.text1EditText).text.toString()
                val Price = Integer.parseInt(dialogView.findViewById<EditText>(R.id.text2EditText).text.toString())

                // 이제 데이터 넘겨주고 리사이클뷰에 추가할 부분
                val request = RegiMenuReq( meueName,Price)
                val data = RegisterMenuReq(request, "이미지들어갈부분")
                Log.d("잘들어감?", "${data}")

                dialogInterface.dismiss()
            }
            .setNegativeButton("닫기") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }

        alertDialog = dialogBuilder.create()
        alertDialog?.show()
        val addImgBtn = dialogView.findViewById<ImageView>(R.id.menu_img)
        addImgBtn.setOnClickListener{
            Log.d("아아아","ㅇ악")
            openGalleryForImage()
        }
    }
    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var MenuListDataList : ArrayList <MenuListData> = arrayListOf(
            MenuListData(R.drawable.logo1,"까사꼬치",87),
            MenuListData(R.drawable.logo2,"까사꼬치",87),
            MenuListData(R.drawable.logo3,"까사꼬치",87),
            MenuListData(R.drawable.logo3,"까사꼬치",87),
            MenuListData(R.drawable.logo3,"까사꼬치",87),
            MenuListData(R.drawable.logo3,"까사꼬치",87),
            MenuListData(R.drawable.logo3,"까사꼬치",87),
            MenuListData(R.drawable.logo3,"까사꼬치",87),
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