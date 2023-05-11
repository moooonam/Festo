package com.example.festo.booth_ui.home


import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R
import com.example.festo.data.API.BoothAPI
import com.example.festo.data.API.UserAPI
import com.example.festo.data.req.BoothStatusReq
import com.example.festo.data.req.RegiMenuReq
import com.example.festo.data.req.RegisterMenuReq
import com.example.festo.data.res.BoothDetailRes
import com.example.festo.data.res.BoothMenuListRes
import com.example.festo.data.res.RegisterBoothRes
import com.example.festo.databinding.FragmentBoothHomeBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

//class MenuListData(
//    var image: Int? = null,
//    var name: String? = null,
//    var price: Int? = null,
//)

class BoothHomeFragment : Fragment() {
    private var retrofit = RetrofitClient.client
    private var menuList = emptyList<BoothMenuListRes>()
    private lateinit var imagePart: MultipartBody.Part
    private lateinit var listAdapter: MenuListAdapter
    private var mBinding: FragmentBoothHomeBinding? = null
    private var alertDialog: AlertDialog? = null
    private val galleryLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                val selectedImageUri = data?.data
                val addImgBtn = alertDialog?.findViewById<ImageView>(R.id.menu_img)
                imagePart = getImageBody(data?.data!!)
                addImgBtn?.setImageURI(selectedImageUri)
            }
        }
    private fun getImageBody(uri: Uri): MultipartBody.Part {
        val filePath = getRealPathFromURI(uri)
        val file = File(filePath)
        val requestFile = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), file)
        return MultipartBody.Part.createFormData("productImage", file.name, requestFile)
    }

    fun getRealPathFromURI(path: Uri): String {

        var proj: Array<String> = arrayOf(MediaStore.Images.Media.DATA)
        var c: Cursor? = requireActivity().contentResolver.query(path, proj, null, null, null)
        var index = c?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c?.moveToFirst()
        var result = c?.getString(index!!)

        return result!!
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

        return mBinding?.root
    }

    private fun showAddMenuDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_addmenu, null)

        val dialogBuilder = AlertDialog.Builder(requireContext())
            .setView(dialogView)
            .setTitle("메뉴 추가")
            .setPositiveButton("추가하기") { dialogInterface, _ ->
                val photo = dialogView.findViewById<ImageView>(R.id.menu_img)
                val meueName = dialogView.findViewById<EditText>(R.id.text1EditText).text.toString()
                if (meueName.isEmpty()) {
                    Toast.makeText(requireActivity(), "메뉴 이름을 입력해 주세요", Toast.LENGTH_SHORT).show()
                } else if (dialogView.findViewById<EditText>(R.id.text2EditText).text.toString()
                        .isEmpty()
                ) {
                    Toast.makeText(requireActivity(), "가격을 입력해 주세요", Toast.LENGTH_SHORT).show()
                } else {
                    val Price =
                        Integer.parseInt(dialogView.findViewById<EditText>(R.id.text2EditText).text.toString())

                    // 이제 데이터 넘겨주고 리사이클뷰에 추가할 부분
                    val request = RegiMenuReq(meueName, Price)
                    val data = RegisterMenuReq(request, imagePart)
                    Log.d("잘들어감?", "${data}")
                    val sharedPreferences =
                        requireContext().getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
                    val myValue = sharedPreferences.getString("myToken", "")
                    val token = "$myValue"
                    fun postRegisterMenu() {
                        Log.d("이미지파트", "${imagePart}")
                        val postApi = retrofit?.create(BoothAPI::class.java)
                        postApi!!.registerMenu(
                            token,"4", request, imagePart
                        )
                            .enqueue(object : Callback<Long> {
                                override fun onResponse(
                                    call: Call<Long>,
                                    response: Response<Long>
                                ) {
                                    Log.d(
                                        "부스메뉴테스트트",
                                        "${response.isSuccessful()}, ${response.code()}, ${response}"
                                    )
                                }

                                override fun onFailure(call: Call<Long>, t: Throwable) {
                                    t.printStackTrace()
                                    Log.d("테스트트트트트", "시래패패패패패패패패패패패퍂패패패")
                                }
                            })
                    }
                    postRegisterMenu()
                    dialogInterface.dismiss()
                }
            }
            .setNegativeButton("닫기") { dialogInterface, _ ->
                dialogInterface.dismiss()
            }

        alertDialog = dialogBuilder.create()
        alertDialog?.show()
        val addImgBtn = dialogView.findViewById<ImageView>(R.id.menu_img)
        addImgBtn.setOnClickListener {
            Log.d("아아아", "ㅇ악")
            openGalleryForImage()
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        galleryLauncher.launch(intent)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        var MenuListDataList: ArrayList<MenuListData> = arrayListOf(
//            MenuListData(R.drawable.logo1, "까사꼬치", 87),
//            MenuListData(R.drawable.logo2, "까사꼬치", 87),
//            MenuListData(R.drawable.logo3, "까사꼬치", 87),
//            MenuListData(R.drawable.logo3, "까사꼬치", 87),
//            MenuListData(R.drawable.logo3, "까사꼬치", 87),
//            MenuListData(R.drawable.logo3, "까사꼬치", 87),
//            MenuListData(R.drawable.logo3, "까사꼬치", 87),
//            MenuListData(R.drawable.logo3, "까사꼬치", 87),
//        )


        // 부스 상세정보 retrofit. 일단 1로 고정해놨음
        val postApi = retrofit?.create(UserAPI::class.java)
        var boothStatus: String // 부스 현재 상태
        var change = "CLOSE"// 부스 상태 바꿀때 보낼 req
        postApi!!.getBoothDetail("1").enqueue(object : Callback<BoothDetailRes> {
            override fun onResponse(
                call: Call<BoothDetailRes>,
                response: Response<BoothDetailRes>
            ) {
                if (response.isSuccessful) {
                    println("성공!!!!!!!!!!!!!!!!!!!")
                    println(response.body())
                    Log.d(" 테스트", "${response.body()}")
                    val boothNameTop = view.findViewById<TextView>(R.id.boothNameTop)
                    val boothName = view.findViewById<TextView>(R.id.boothName)
                    val boothOperatingTime = view.findViewById<TextView>(R.id.boothOperatingTime)
                    val boothLocation = view.findViewById<TextView>(R.id.boothLocation)
                    val statusBtn = view.findViewById<Button>(R.id.statusBtn)
                    boothStatus = response.body()?.status.toString()
                    if (boothStatus == "OPEN") {
                        statusBtn.text = "영업종료"
                        change = "CLOSE"
                    } else {
                        statusBtn.text = "영업시작"
                        change = "OPEN"
                    }

                    // 데이터 xml에 입력
                    boothNameTop.text = response.body()?.name
                    boothName.text = response.body()?.name
                    boothLocation.text = response.body()?.locationDescription
                    boothOperatingTime.text =
                        "${response.body()?.openTime} ~ ${response.body()?.closeTime}"
                }
            }

            override fun onFailure(call: Call<BoothDetailRes>, t: Throwable) {
                println("실패!!!!!!!!!!!!!!!!!!!")
                t.printStackTrace()
            }
        })


        // 부스 메뉴 리스트 retrofit
        val tokenValue =
            "eyJ0eXAiOiJKV1QiLCJyZWdEYXRlIjoxNjgzNzIzODM1LCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2ODg5MDc4MzUsInN1YiI6IjIiLCJpc3MiOiJPdG16IiwiaWF0IjoxNjgzNzIzODM1fQ.TtSFsz7ScldLe5Ny1WhDX8oxs_L9Dz12BQ0d4_6AePo"
        val token = "Bearer $tokenValue"
        postApi!!.getBoothMenuList(token).enqueue(object : Callback<List<BoothMenuListRes>> {
            override fun onResponse(
                call: Call<List<BoothMenuListRes>>,
                response: Response<List<BoothMenuListRes>>
            ) {
                if (response.isSuccessful) {
                    println("성공!!!!!!!!!!!!!!!!!!!")
                    println(response.body())
                    Log.d(" 테스트", "${response.body()?.get(0)?.cnt}")
                    menuList = response.body() ?: emptyList()
                    // 메뉴 리스트 연결
                    listAdapter = MenuListAdapter(menuList)
                    mBinding?.menulistFragmentListView?.layoutManager =
                        LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
                    mBinding?.menulistFragmentListView?.adapter = listAdapter

                }
            }

            override fun onFailure(call: Call<List<BoothMenuListRes>>, t: Throwable) {
                println("실패!!!!!!!!!!!!!!!!!!!")
                t.printStackTrace()
            }
        })

        val changeStatusBtn = view.findViewById<Button>(R.id.statusBtn)
        changeStatusBtn.setOnClickListener {
            // 부스 영업 상태 변경 retrofit
            val postApiStatus = retrofit?.create(BoothAPI::class.java)
            postApiStatus!!.changeBoothStatus("1", BoothStatusReq(change))
                .enqueue(object : Callback<Void> {
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful) {
                            println("상태변경성공!!!!!!!!!!!!!!!!!!!")
                            postApi!!.getBoothDetail("1").enqueue(object : Callback<BoothDetailRes> {
                                override fun onResponse(
                                    call: Call<BoothDetailRes>,
                                    response: Response<BoothDetailRes>
                                ) {
                                    if (response.isSuccessful) {
                                        val statusBtn = view.findViewById<Button>(R.id.statusBtn)
                                        boothStatus = response.body()?.status.toString()
                                        if (boothStatus == "OPEN") {
                                            statusBtn.text = "영업종료"
                                            change = "CLOSE"
                                        } else {
                                            statusBtn.text = "영업시작"
                                            change = "OPEN"
                                        }

                                    }
                                }

                                override fun onFailure(call: Call<BoothDetailRes>, t: Throwable) {
                                    println("실패!!!!!!!!!!!!!!!!!!!")
                                    t.printStackTrace()
                                }
                            })
                        }
//                println("오잉!!!!!!!!!!!!!!!!!!!")
//                println(response)
                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        println("상태변경실패!!!!!!!!!!!!!!!!!!!")
                        t.printStackTrace()
                    }
                })
        }
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }

}