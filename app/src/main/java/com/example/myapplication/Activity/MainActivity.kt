package com.example.myapplication.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.Adapter.CategoryAdapter
import com.example.myapplication.Adapter.TopDoctorAdapter
import com.example.myapplication.R
import com.example.myapplication.ViewModel.MainViewModel
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel=MainViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        initCategory()
        initTopDoctors()




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun initTopDoctors() {
        binding.apply {
            progressBarTopDoctor.visibility=View.VISIBLE
            viewModel.doctors.observe(this@MainActivity, Observer {
                recyclerViewTopDoctor.layoutManager=LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL, false)
                recyclerViewTopDoctor.adapter=TopDoctorAdapter(it)
                progressBarTopDoctor.visibility=View.GONE
            })
            viewModel.loadDoctors()

            doctorListTxt.setOnClickListener {
                startActivity(Intent(this@MainActivity,TopDoctorsActivity::class.java))
            }
        }
    }

    private fun initCategory() {
     binding.progressBarCategory.visibility=View.VISIBLE
        viewModel.category.observe(this, Observer {
            binding.viewCategory.layoutManager=
                LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL,false)
            binding.viewCategory.adapter=CategoryAdapter(it)
            binding.progressBarCategory.visibility=View.GONE

        })
        viewModel.loadCategory()
    }
}