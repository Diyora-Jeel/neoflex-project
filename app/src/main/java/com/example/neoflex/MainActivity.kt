package com.example.neoflex

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.neoflex.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "Neoflex"

        binding.bollywood.setOnClickListener {
            val intent = Intent(this@MainActivity, MovieActivity::class.java)
            intent.putExtra("movieCategory", "Bollywood")
            startActivity(intent)
        }

        binding.hollywood.setOnClickListener {
            val intent = Intent(this@MainActivity, MovieActivity::class.java)
            intent.putExtra("movieCategory", "Hollywood")
            startActivity(intent)
        }

        binding.south.setOnClickListener {
            val intent = Intent(this@MainActivity, MovieActivity::class.java)
            intent.putExtra("movieCategory", "South")
            startActivity(intent)
        }

        binding.gujarati.setOnClickListener {
            val intent = Intent(this@MainActivity, MovieActivity::class.java)
            intent.putExtra("movieCategory", "Gujarati")
            startActivity(intent)
        }
    }
}