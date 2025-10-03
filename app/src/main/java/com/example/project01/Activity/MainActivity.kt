package com.example.project01.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View // Import View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide // Import Glide
import com.example.project01.Adapter.CategoryAdapter
import com.example.project01.Adapter.PopularAdapter
import com.example.project01.R
import com.example.project01.ViewModel.MainViewModel
import com.example.project01.databinding.ActivityMainBinding
// Remove the incorrect import if it exists:
// import com.example.project01.databinding.ActivitySplashBinding

class MainActivity : AppCompatActivity() {
    // Change the type of binding to ActivityMainBinding
    private lateinit var binding: ActivityMainBinding
    private val  viewModel= MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Ensure you are inflating the correct layout
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // It seems you might want to adjust window insets for binding.root
        // ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
        //     val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        //     v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        //     insets
        // }

        initBanner()
        initCategory()
        initPopular()
        initBottomMenu()
    }

    private fun initBottomMenu() {
        binding.cartBtn.setOnClickListener {
            startActivity(Intent(this,CartActivity::class.java))
        }
    }

    private fun initPopular() {
        binding.progressBarPopular.visibility= View.VISIBLE
        viewModel.loadPopular().observeForever {
            binding.recyclerViewPopular.layoutManager= GridLayoutManager(this,2)
            binding.recyclerViewPopular.adapter= PopularAdapter(it)
            binding.progressBarPopular.visibility= View.GONE
        }
        viewModel.loadPopular()
    }

    private fun initCategory() {
        binding.progressBarCategory.visibility= View.VISIBLE
        viewModel.loadCategory().observeForever {
            binding.categoryView
                .layoutManager = LinearLayoutManager(
                this@MainActivity, LinearLayoutManager.HORIZONTAL, false
            )
            binding.categoryView.adapter = CategoryAdapter(it)
            binding.progressBarCategory.visibility= View.GONE
        }
        viewModel.loadCategory()
    }

    private fun initBanner() {
        binding.progressBarBanner.visibility= View.VISIBLE
        viewModel.loadBanner().observeForever {
            // Make sure 'it' is not empty and contains the expected data
            if (it.isNotEmpty()) {
                Glide.with(this@MainActivity)
                    .load(it[0].url)
                    .into(binding.banner)
            }
            binding.progressBarBanner.visibility= View.GONE
        }
        // You are calling loadBanner() twice, you might want to remove one
        // viewModel.loadBanner()
    }
}
