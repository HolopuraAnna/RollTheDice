package com.example.rollthedice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.delay
import kotlin.random.Random


class DiceViewModel : ViewModel() {
    private val _diceStates = MutableLiveData<List<Int>>(listOf(6, 6, 6, 6, 6))
    val diceStates: LiveData<List<Int>> = _diceStates

    private val _isRolling = MutableLiveData(false)
    val isRolling: LiveData<Boolean> = _isRolling

    suspend fun rollDice() {
        _isRolling.value = true
        val startTime = System.currentTimeMillis()  ///////
        while (_isRolling.value == true) {
            while (System.currentTimeMillis() - startTime < 10000) {   //////
                val newStates = List(5) { Random.nextInt(1, 7) }
                _diceStates.postValue(newStates)
                delay(100)
            }
        }
    }

    fun startRolling() {
        _isRolling.value = true
    }

    fun stopRolling() {
        _isRolling.value = false
    }
}