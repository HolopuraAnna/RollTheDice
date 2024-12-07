package com.example.rollthedice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random


class DiceViewModel : ViewModel() {
    private val _diceStates = MutableLiveData<List<Int>>(listOf(6, 6, 6, 6, 6))
    val diceStates: LiveData<List<Int>> = _diceStates

    suspend fun rollDice() {
        val newStates = List(5) { Random.nextInt(1, 7) }
        _diceStates.postValue(newStates)
        delay(100)
    }
}