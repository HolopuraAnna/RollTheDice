package com.example.rollthedice

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var diceImages: List<ImageView>
    private val diceViewModel: DiceViewModel by viewModels()

    private var rollingJob: Job? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        diceImages = listOf(
            findViewById(R.id.ImageView1),
            findViewById(R.id.ImageView2),
            findViewById(R.id.ImageView3),
            findViewById(R.id.ImageView4),
            findViewById(R.id.ImageView5)
        )

        val rollButton: Button = findViewById(R.id.RollButton)
        val stopButton: Button = findViewById(R.id.StopButton)

        diceViewModel.diceStates.observe(this) { diceStates ->
            for (i in diceStates.indices) {
                diceImages[i].setImageResource(getDiceDrawable(diceStates[i]))
            }
        }

        diceViewModel.isRolling.observe(this) { isRolling ->
            if (isRolling && rollingJob == null) {
                startRolling()
            }
        }

        rollButton.setOnClickListener {
            diceViewModel.startRolling()
        }

        stopButton.setOnClickListener {
            diceViewModel.stopRolling()
        }
    }

    private fun startRolling() {
        rollingJob = lifecycleScope.launch {
            diceViewModel.rollDice()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        rollingJob?.cancel()
    }

    private fun getDiceDrawable(number: Int): Int {
        return when (number) {
            1 -> R.drawable.one
            2 -> R.drawable.two
            3 -> R.drawable.three
            4 -> R.drawable.four
            5 -> R.drawable.five
            6 -> R.drawable.six
            else -> R.drawable.six
        }
    }
}