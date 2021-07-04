package com.example.mytodolist.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytodolist.MainActivity
import com.example.mytodolist.R
import com.example.mytodolist.adapters.MainAdapter
import com.example.mytodolist.databinding.MainFragmentBinding
import com.example.mytodolist.ui.main.viewmodel.MainFragmentViewModel

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*



    @AndroidEntryPoint
    class MainFragment : Fragment(R.layout.main_fragment) {

        private val viewModel: MainFragmentViewModel by viewModels()


        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)


            val binding = MainFragmentBinding.bind(view)
            val mainAdapter = MainAdapter()
            binding.apply {
                mainRecyclerView.apply {
                    adapter = mainAdapter
                    layoutManager = LinearLayoutManager(requireContext())
                    setHasFixedSize(true)
                }
            }
            viewModel.tasks.observe(viewLifecycleOwner){
                mainAdapter.submitList(it)
            }


            calendar_view.setOnDateChangeListener { view, year, month, dayOfMonth ->
                val bundle = Bundle()
                val data = "$dayOfMonth.${month + 1}.$year"
                bundle.putString("DayArg", data)
                (activity as MainActivity).navController.navigate(R.id.viewTask, bundle)
            }

        }
    }



