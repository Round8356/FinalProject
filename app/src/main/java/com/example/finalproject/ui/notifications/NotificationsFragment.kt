package com.example.finalproject.ui.notifications


import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.R
import kotlinx.android.synthetic.main.fragment_notifications.*
import java.io.IOException
import kotlin.concurrent.thread

class NotificationsFragment: Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    val mediaPlayer= MediaPlayer()
    var ifopen:Boolean=true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mediaPlayer.setOnPreparedListener{
            it.start()
        }
        mediaPlayer.setOnCompletionListener {
            notificationsViewModel.OnCompletionListener()
            //play()
        }
        if(ContextCompat.checkSelfPermission(requireActivity(),android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),0)
        }
        else{
            getMusicList()
        }
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean)
            {
                if(fromUser){
                    mediaPlayer.seekTo(progress)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        button_play.setOnClickListener {
            play()
        }
        button_pause.setOnClickListener{
            if(notificationsViewModel.isPauses.value!!){
                mediaPlayer.start()
                notificationsViewModel.nopause()
            }else{
                mediaPlayer.pause()
                notificationsViewModel.pause()
            }
        }
        button_Stop.setOnClickListener {
            mediaPlayer.stop()
        }
        button_next.setOnClickListener {
            notificationsViewModel.OnCompletionListener()
            play()
        }
        button_prev.setOnClickListener {
            notificationsViewModel.onprev()
            play()
        }

        thread {
            while (true){
                //Log.d("flag________________","${flag}")
                if(ifopen.equals(false)){
                    break
                }else{
                    requireActivity().runOnUiThread{
                        seekBar.max=mediaPlayer.duration
                        seekBar.progress=mediaPlayer.currentPosition
                    }
                }
                Thread.sleep(1000)
            }
        }
    }

    fun play(){
        if(notificationsViewModel.musicLists.value?.size==0) return
        val path=notificationsViewModel.musicLists.value?.get(notificationsViewModel.currents.value!!)
        mediaPlayer.reset()
        try {
            mediaPlayer.setDataSource(path)
            mediaPlayer.prepareAsync()
            textView_mn.text=notificationsViewModel.musicNameLists.value!![notificationsViewModel.currents.value!!]
            textView_count.text="${notificationsViewModel.currents.value!!+1}/${notificationsViewModel.musicLists.value?.size}"
        }catch(e: IOException){
            e.printStackTrace()
        }

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        getMusicList()
    }

    override fun onPause() {
        super.onPause()
        ifopen=false
    }

    override fun onDetach() {
        super.onDetach()
        mediaPlayer.release()
    }


    private fun getMusicList(){
        notificationsViewModel.getMusicList()

    }
}