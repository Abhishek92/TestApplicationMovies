package com.example.testapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapplication.adapter.MovieListAdapter
import com.example.testapplication.databinding.ActivityMainBinding
import com.example.testapplication.network.Failure
import com.example.testapplication.network.InputParams
import com.example.testapplication.network.Success
import com.example.testapplication.viewmodel.MovieListViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MovieListViewModel by viewModels()
    private var adapter: MovieListAdapter? = null
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getMovieList(InputParams(page = 1)).observe(this) {
            when(it){
                is Success -> {
                    if(adapter == null){
                        adapter = MovieListAdapter(this, it.data.results)
                        binding.recyclerViewMovieList.adapter = adapter
                        binding.recyclerViewMovieList.layoutManager = LinearLayoutManager(this)
                    }
                }
                is Failure -> Toast.makeText(this, "Error fetching movie list: ${it.message}", Toast.LENGTH_SHORT).show()
            }
        }

        binding.fabFilter.setOnClickListener {

        }
    }
}