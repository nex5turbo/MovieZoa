package wonyong.by.moviezoa

import android.app.PendingIntent.getActivity
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import wonyong.by.moviezoa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var service:API
    lateinit var retrofit: Retrofit
    lateinit var adapter:MovieRecyclerAdapter

    var currentTitle = ""
    var currentPage = 1
    var currentTotalPage = 0
    var data = ArrayList<Models.MovieInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this
        retrofit = RetrofitClient.getInstnace()
        service = retrofit.create(API::class.java)
        init()
    }
    private fun initAdapterListener(){
        adapter.itemClickListener = object :MovieRecyclerAdapter.OnItemClickListener{
            override fun OnItemClick(
                holder: MovieRecyclerAdapter.MyViewHolder,
                view: View,
                data: Models.MovieInfo,
                position: Int
            ) {
                Log.d("###", "test")
                val dialog:InformationDialog = InformationDialog(this@MainActivity, data)
                val drawable =
                    this@MainActivity.getDrawable(R.drawable.image_view_rounding) as GradientDrawable
                dialog.getWindow()!!.setBackgroundDrawable(drawable)
                dialog.getWindow()!!.setClipToOutline(true)
                dialog.show()
            }
        }
    }
    private fun init() {
        binding.recyclerView.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.VERTICAL,
            false
        )
        adapter = MovieRecyclerAdapter(data)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = binding.recyclerView.layoutManager
                if (currentPage < currentTotalPage) {
                    val lastVisibledItem = (layoutManager as LinearLayoutManager)
                        .findLastCompletelyVisibleItemPosition()
                    if (layoutManager.itemCount <= lastVisibledItem + 1) {
                        requestNextPage()
                    }
                }
            }
        })
        binding.searchButton.setOnClickListener {
            var title = binding.titleEdit.text.toString()
            requestSearch(title)
        }
    }
    fun requestSearch(title: String){
        currentTitle = title
        currentPage = 1
        service.getMovieSearch(Constants.TMDB_KEY, title, "ko-KR", currentPage).enqueue(object :
            Callback<Models.Movie> {
            override fun onResponse(call: Call<Models.Movie>, response: Response<Models.Movie>) {
                Log.d("####", response.body().toString())
                if (response.body()!!.total_results == 0) {
                    data.clear()
                    adapter = MovieRecyclerAdapter(data)
                    binding.recyclerView.adapter = adapter
                    Toast.makeText(this@MainActivity, "검색결과가 없습니다.", Toast.LENGTH_SHORT).show()
                } else {
                    currentTotalPage = response.body()!!.total_pages
                    data = response.body()!!.movieList
                    Log.d("####", data[0].toString())
                    adapter = MovieRecyclerAdapter(data)
                    initAdapterListener()
                    binding.recyclerView.adapter = adapter
                }
            }

            override fun onFailure(call: Call<Models.Movie>, t: Throwable) {
                Log.d("###", call.toString())
                Log.d("###", t.toString())
            }

        })
    }
    fun requestNextPage(){
        if(currentPage == currentTotalPage){
            return
        }
        currentPage++
        service.getMovieSearch(Constants.TMDB_KEY, currentTitle, "ko-KR", currentPage).enqueue(
            object : Callback<Models.Movie> {
                override fun onResponse(
                    call: Call<Models.Movie>,
                    response: Response<Models.Movie>
                ) {
                    var nextData = response.body()!!.movieList
                    for (temp in nextData) {
                        data.add(temp)
                    }
                    adapter.notifyDataSetChanged()
//                binding.invalidateAll()
                }

                override fun onFailure(call: Call<Models.Movie>, t: Throwable) {
                    Log.d("###", call.toString())
                    Log.d("###", t.toString())
                }

            })
    }

}