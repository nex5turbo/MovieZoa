package wonyong.by.moviezoa

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import wonyong.by.moviezoa.*
import wonyong.by.moviezoa.databinding.ActivitySimilarBinding
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import java.util.concurrent.ExecutionException

class SimilarActivity : AppCompatActivity() {
    lateinit var retrofit: Retrofit
    lateinit var service:API
    lateinit var binding:ActivitySimilarBinding
    var displayInfo: DisplayInfo? = null
    var id = 0
    var data: ArrayList<Models.MovieInfo> = ArrayList<Models.MovieInfo>()
    var adapter: SimilarRecyclerAdapter? = null
    var currentPage = 1
    var currentTotalPage = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_similar)
        retrofit = RetrofitClient.getInstnace()
        service = retrofit.create(API::class.java)
        displayInfo = DisplayInfo(this)
        initData()
        getData()

    }

    private fun getData(){
        service.getSimilarSearch(id,Constants.TMDB_KEY,"ko-KR", currentPage).enqueue(object :Callback<Models.Movie>{
            override fun onResponse(call: Call<Models.Movie>, response: Response<Models.Movie>) {
                data=response.body()!!.movieList
                currentTotalPage = response.body()!!.total_pages
                initRecyclerView()
            }

            override fun onFailure(call: Call<Models.Movie>, t: Throwable) {
                Log.d("###", "failed")
            }

        })
    }
    private fun requestNextPage(){
        if(currentPage == currentTotalPage){
            return
        }
        currentPage++
        service.getSimilarSearch(id,Constants.TMDB_KEY,"ko-KR", currentPage).enqueue(object :Callback<Models.Movie>{
            override fun onResponse(call: Call<Models.Movie>, response: Response<Models.Movie>) {
                var tempData=response.body()!!.movieList
                for(temp in tempData){
                    data.add(temp)
                }
                adapter!!.notifyDataSetChanged()
            }

            override fun onFailure(call: Call<Models.Movie>, t: Throwable) {
                Log.d("###", "failed")
            }

        })
    }

    private fun initRecyclerView() {
        binding.similarRecyclerview.layoutManager = GridLayoutManager(this, 2, RecyclerView.HORIZONTAL, false)
        adapter = SimilarRecyclerAdapter(data, displayInfo!!)
        binding.similarRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.similarRecyclerview.layoutManager
                if (currentPage < currentTotalPage) {
                    val lastVisibledItem = (layoutManager as LinearLayoutManager)
                        .findLastCompletelyVisibleItemPosition()
                    if (layoutManager.itemCount <= lastVisibledItem + 1) {
                        requestNextPage()
                    }
                }
            }
        })
        adapter!!.itemClickListener = object : SimilarRecyclerAdapter.OnItemClickListener{
            override fun OnItemClick(
                holder: SimilarRecyclerAdapter.MyViewHolder,
                view: View,
                data: Models.MovieInfo,
                position: Int
            ) {
                val dialog:InformationDialog = InformationDialog(this@SimilarActivity, data)
                val drawable =
                    this@SimilarActivity.getDrawable(R.drawable.image_view_rounding) as GradientDrawable
                dialog.getWindow()!!.setBackgroundDrawable(drawable)
                dialog.getWindow()!!.setClipToOutline(true)
                dialog.show()
            }
        }
        binding.similarRecyclerview.adapter = adapter
    }

    private fun initData() {
        val intent = intent
        id = intent.getIntExtra("id", -1)
    }
}