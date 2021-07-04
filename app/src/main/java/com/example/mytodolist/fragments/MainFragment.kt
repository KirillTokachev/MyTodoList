    package com.example.mytodolist.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnDayClickListener
import com.applandeo.materialcalendarview.utils.calendar
import com.example.mytodolist.MainActivity
import com.example.mytodolist.R
import com.example.mytodolist.adapters.MainAdapter
import com.example.mytodolist.databinding.MainFragmentBinding
import com.example.mytodolist.ui.main.viewmodel.MainFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*
import java.util.*


    @AndroidEntryPoint
    class MainFragment : Fragment(R.layout.main_fragment) {

        private val viewModel: MainFragmentViewModel by viewModels()

        private val events: MutableList<EventDay> = ArrayList()

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)


            events.add(EventDay(calendar, R.drawable.example_today_bg))
            calendar_view.setEvents(events)



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

            val bundle = Bundle()

            calendar_view.setOnDayClickListener(object : OnDayClickListener {
                override fun onDayClick(eventDay: EventDay) {
                    val clickedDayCalendar = eventDay.calendar
                    (activity as MainActivity).navController.navigate(R.id.viewTask, bundle)
                }
            })


            /*calendar_view.setOnDateChangeListener { view, year, month, dayOfMonth ->

                val data = "$dayOfMonth.${month + 1}.$year"
                bundle.putString("DayArg", data)
                (activity as MainActivity).navController.navigate(R.id.viewTask, bundle)
            }*/

        }


    }



