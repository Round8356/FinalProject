package com.example.finalproject.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.edu.sicnu.cardgame.CardMatchingGame

class DashboardViewModel : ViewModel() {

    companion object {
        private lateinit var game: CardMatchingGame
    }
    private var _game:MutableLiveData<CardMatchingGame> = MutableLiveData()
    val games=_game

    init {
        _game.value=CardMatchingGame(24)
    }

    fun reset(){
        _game.value?.reset()
    }

    fun chooseCardAtIndex(i:Int){
        _game.value?.chooseCardAtIndex(i)
    }
}