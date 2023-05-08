package com.example.festo.customer_ui.mypage

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.festo.R
import com.example.festo.RetrofitImpl.apiService
import com.example.festo.booth_ui.no_booth.NoBoothMainActivity
import com.example.festo.databinding.FragmentMypageBinding
import com.example.festo.host_ui.no_festival.NoFeativalMainActivity
import com.google.gson.annotations.SerializedName
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecentOrderListData(
    var date: String? = null,
    var booth: String? = null,
    var price: Int? = null,
    var boothImg: Int? = null,
)


//test
data class MovieResponse(
    val page: Int,
    val results: List<Movie>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)

data class Movie(
    val id: Int,
    val title: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val popularity: Double,
    val voteCount: Int,
    val voteAverage: Double
)

class MypageFragment : Fragment() {
    private lateinit var listAdapter: RecentOrderListAdapter
    private var mBinding: FragmentMypageBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding = FragmentMypageBinding.inflate(inflater, container, false)

        mBinding!!.ivProfile2.setOnClickListener{
            val intent = Intent(getActivity(), NoBoothMainActivity::class.java)
            startActivity(intent)
        }

        mBinding!!.ivProfile3.setOnClickListener{
            val intent = Intent(getActivity(), NoFeativalMainActivity::class.java)
            startActivity(intent)
        }

        return  mBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val RecentOrderListDataList: ArrayList<RecentOrderListData> = arrayListOf(
            RecentOrderListData("23.04.26", "광야야야야야앙", 23000, R.drawable.logo1),
            RecentOrderListData("23.04.27", "광야야야야야앙", 23000, R.drawable.logo1),
            RecentOrderListData("23.04.28", "광야야야야야앙", 23000, R.drawable.logo2),
        )

        listAdapter = RecentOrderListAdapter(RecentOrderListDataList)
        mBinding?.recentOderListListView?.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        mBinding?.recentOderListListView?.adapter = listAdapter

        // Retrofit으로 OpenAPI 호출하기
        val call = apiService.getTopRatedMovies("026ea60489848eb170d1cd15b54c6f91")

        call.enqueue(object : Callback<MovieResponse> {
            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    // 영화 정보를 가져와서 처리하는 로직을 추가합니다.
                    println("가져오기 성공!!!!!!!!!!!!!!!!!!")
                    println(movies)
                } else {
                    // API 호출이 실패한 경우 처리할 로직을 추가합니다.
                    println("가져오기 실패!!!!!!!!!!!!!!!!!!")
                }
            }

            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {
                // API 호출이 실패한 경우 처리할 로직을 추가합니다.
                println("가져오기 실패2!!!!!!!!!!!!!!!!!!")
            }
        })
    }

    override fun onDestroyView() {
        mBinding = null
        super.onDestroyView()
    }
}
