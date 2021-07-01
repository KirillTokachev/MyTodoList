package com.example.mytodolist.ui.main.viewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.R
class MainActivity : AppCompatActivity() {

//    private lateinit var mainViewModel: MainViewModel
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)


        /*mainViewModel = ViewModelProvider(this, MainFactory(application, text = "Factory")).get(MainViewModel::class.java)*/
        navController = Navigation.findNavController(this, R.id.navHost)

    }

}
