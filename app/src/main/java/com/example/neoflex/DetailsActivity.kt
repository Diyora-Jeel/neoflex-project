package com.example.neoflex

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.example.neoflex.constants.CommonUtils
import com.example.neoflex.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {

    lateinit var binding : ActivityDetailsBinding

    private lateinit var commonUtils: CommonUtils
    private lateinit var dialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar

        init()

        val movieId = intent.getStringExtra("movieId").toString()
        val movieTitle = intent.getStringExtra("movieTitle").toString()
        val movieLink = intent.getStringExtra("movieLink").toString()

        val url = "https://www.omdbapi.com/?i=$movieId&apikey=82cf804b"

        val queue: RequestQueue = Volley.newRequestQueue(applicationContext)

        val request = JsonObjectRequest(Request.Method.GET, url, null, { response ->
            try {
                val poster: String = response.getString("Poster")
                val title: String = response.getString("Title")
                val year: String = response.getString("Year")
                val rated: String = response.getString("Rated")
                val runtime: String = response.getString("Runtime")
                val released: String = response.getString("Released")
                val genre: String = response.getString("Genre")
                val director: String = response.getString("Director")
                val writer: String = response.getString("Writer")
                val actors: String = response.getString("Actors")
                val plot: String = response.getString("Plot")
                val language: String = response.getString("Language")
                val country: String = response.getString("Country")
                val metaScore: String = response.getString("Metascore")
                val imdbRating: String = response.getString("imdbRating")
                val imdbVotes: String = response.getString("imdbVotes")
                val boxOffice: String = response.getString("BoxOffice")
                val awards: String = response.getString("Awards")

                Glide.with(this).load(poster).placeholder(R.drawable.ic_no_image).into(binding.posterIV)
                actionBar!!.title = title
                binding.titleTv.text = title
                binding.infoTV.text = "$year  -  $rated  -  $runtime"
                binding.plotTV.text = plot
                binding.imdbRating.text = imdbRating
                binding.imdbVotes.text = imdbVotes
                binding.metaScore.text = metaScore
                binding.releasedTv.text = released
                binding.countryTV.text = country
                binding.languageTv.text = language
                binding.directorTv.text = director
                binding.writersTv.text = writer
                binding.actorsTv.text = actors
                binding.boxOfficeTv.text = boxOffice
                binding.genreTv.text = genre
                binding.awardsTv.text = awards

                commonUtils.dismissCustomDialog(dialog)

            } catch (e: Exception) {
                commonUtils.dismissCustomDialog(dialog)
                e.printStackTrace()
            }

        }, { error ->
            Toast.makeText(this@DetailsActivity, "Error $error", Toast.LENGTH_SHORT).show()
            commonUtils.dismissCustomDialog(dialog)
        })
        queue.add(request)

        binding.watchNowTv.setOnClickListener {
            val intent  = Intent(this@DetailsActivity,PlayerActivity::class.java)
            intent.putExtra("movieTitle",movieTitle)
            intent.putExtra("movieLink",movieLink)
            startActivity(intent)
        }
    }

    private fun init() {
        commonUtils = CommonUtils()
        dialog = commonUtils.createCustomLoader(this@DetailsActivity, false)
        commonUtils.showCustomDialog(dialog, this@DetailsActivity)
    }
}