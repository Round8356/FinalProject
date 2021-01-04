package com.example.finalproject.ui.notifications

import android.app.Application
import android.content.ContentValues
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NotificationsViewModel (application: Application) : AndroidViewModel(application) {

    private var _musicList:MutableLiveData<List<String>> = MutableLiveData()
    private var _musicNameList:MutableLiveData<List<String>> = MutableLiveData()
    private var _current :MutableLiveData<Int> = MutableLiveData()
    private var _isPause:MutableLiveData<Boolean> = MutableLiveData()
    val musicLists: LiveData<List<String>> =_musicList
    val musicNameLists:LiveData<List<String>> = _musicNameList
    val currents:LiveData<Int> = _current
    val isPauses:LiveData<Boolean>  =_isPause
    private val musicList= mutableListOf<String>()
    private val musicNameList= mutableListOf<String>()

    init{
        _isPause.value=false
    }
    fun OnCompletionListener(){
        // _current.value= _current.value?:0+1
        val pos=_current.value ?:0
        _current.value=pos+1
        if(_current.value!! >= _musicList.value!!.size){
            _current.value=0
        }
    }

    fun onprev(){
        val pos=_current.value ?:0
        _current.value=pos-1
        if(_current.value!! <0){
            _current.value=_musicList.value!!.size-1
        }
    }

    fun pause(){
        _isPause.value=true
    }
    fun nopause(){
        _isPause.value=false
    }


    fun getMusicList(){
        val cursor=getApplication<Application>().contentResolver.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,null)
        if(cursor!=null){
            while(cursor.moveToNext()){
                val musicPath=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA))
                musicList.add(musicPath)
                Log.d("music","${musicPath}")
                val musicName=cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE))
                musicNameList.add(musicName)
                Log.d(ContentValues.TAG,"getMusicList:$musicPath name:$musicName")
            }
            _musicList.postValue(musicList)
            _musicNameList.postValue(musicNameList)
        }
        cursor?.close()
    }
}