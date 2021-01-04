package com.example.finalproject.ui.watch

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.example.finalproject.R
import kotlinx.android.synthetic.main.watch_fragment.*


class WatchFragment : Fragment() {

    companion object {
        fun newInstance() = WatchFragment()
    }

    private lateinit var watchViewModel: WatchViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.watch_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        watchViewModel = ViewModelProvider(this).get(WatchViewModel::class.java)
        // TODO: Use the ViewModel
        watchViewModel.seconds.observe(viewLifecycleOwner, Observer {
            val hours=it/3600
            val minutes=(it%3600)/60
            val secs=it % 60
            textView_time.text=String.format("%02d:%02d:%02d",hours,minutes,secs)
        })


        button_start.setOnClickListener {
            watchViewModel.start()
        }
        button_stop.setOnClickListener {
            watchViewModel.stop()
        }
        button_restart.setOnClickListener {
            watchViewModel.restart()
        }
    }

}