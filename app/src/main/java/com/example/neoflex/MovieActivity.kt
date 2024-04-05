package com.example.neoflex

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.neoflex.adapter.WatchMovieAdapter
import com.example.neoflex.constants.CommonUtils
import com.example.neoflex.databinding.ActivityMovieBinding
import com.example.neoflex.model.MovieWatchModel
import com.example.neoflex.onClick.MovieOnClick
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MovieActivity : AppCompatActivity(),MovieOnClick {

    private lateinit var binding : ActivityMovieBinding

    lateinit var database: FirebaseDatabase
    private lateinit var myRef: DatabaseReference

    private lateinit var movieWatchModel: MovieWatchModel
    var dataList = ArrayList<MovieWatchModel>()

    lateinit var watchMovieAdapter: WatchMovieAdapter

    private lateinit var commonUtils: CommonUtils
    private lateinit var dialog: Dialog

    private val adUnitId = "Interstitial_Android"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar

        val movieCategory = intent.getStringExtra("movieCategory").toString()

        actionBar!!.title = "$movieCategory Movie"

        init()

        database = FirebaseDatabase.getInstance("https://neoflex-12e6b-default-rtdb.asia-southeast1.firebasedatabase.app/")
        myRef = database.getReference(movieCategory.lowercase())

        if (commonUtils.isNetworkAvailable(this@MovieActivity)) {
            getData()
        } else {
            commonUtils.createDialog(
                this@MovieActivity,
                R.drawable.ic_no_internet,
                this,
                "No Internet",
                "Please check your connection status and try again"
            )
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText)
                return false
            }
        })
    }

    private fun filter(newText: String?) {

        val filterList: ArrayList<MovieWatchModel> = ArrayList()

        for (item in dataList) {
            if (item.title.toString().lowercase().contains(newText.toString().lowercase())) {
                filterList.add(item)
            }
        }
        if (filterList.isEmpty()) {
            Toast.makeText(this@MovieActivity, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            watchMovieAdapter.filterList(filterList)
        }
    }

    private fun getData() {

        myRef.addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children) {
                    movieWatchModel = MovieWatchModel()
                    movieWatchModel.id = item.child("id").value.toString()
                    movieWatchModel.title = item.child("title").value.toString()
                    movieWatchModel.poster = item.child("poster").value.toString()
                    movieWatchModel.link = item.child("link").value.toString()
                    dataList.add(movieWatchModel)
                }

                if (dataList.isNotEmpty()) {

                    dataList.reverse()

                    binding.recyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
                    watchMovieAdapter = WatchMovieAdapter(dataList, this@MovieActivity, this@MovieActivity)
                    binding.recyclerView.adapter = watchMovieAdapter

                    commonUtils.dismissCustomDialog(dialog)
                }
                else {
                    binding.noDataTv.visibility = View.VISIBLE
                    commonUtils.dismissCustomDialog(dialog)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MovieActivity,error.message,Toast.LENGTH_LONG).show()
                commonUtils.dismissCustomDialog(dialog)
            }
        })

    }

    override fun onMovieData(id: String, title: String, poster: String, link: String) {
        val intent = Intent(this@MovieActivity,DetailsActivity::class.java)
        intent.putExtra("movieId",id)
        intent.putExtra("movieTitle",title)
        intent.putExtra("movieLink",link)
        startActivity(intent)
    }

    private fun init() {
        commonUtils = CommonUtils()
        dialog = commonUtils.createCustomLoader(this@MovieActivity, false)
        commonUtils.showCustomDialog(dialog, this@MovieActivity)
    }
}