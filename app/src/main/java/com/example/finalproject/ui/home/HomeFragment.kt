package com.example.finalproject.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject.R
import com.example.xjw2018110454_10.CityItem
import kotlinx.android.synthetic.main.fragment_home.*
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable
import java.lang.Exception
import java.lang.StringBuilder


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
        homeViewModel.cities.observe(viewLifecycleOwner, Observer {
            val adapter = ArrayAdapter<CityItem>(requireActivity(), android.R.layout.simple_list_item_1, it)
            listView.adapter = adapter
            listView.setOnItemClickListener { adapterView, view, i, l ->
                val cityCode = it[i].city_code
                val intent = Intent(requireActivity(),WeatherActivity::class.java)
                intent.putExtra("city_code",cityCode)
                startActivity(intent)
            }
        })
    }
}