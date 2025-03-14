package com.example.dicegame

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    //dices array
    private val dicesArray = arrayOf(Dice(), Dice(), Dice())

    //store dice images
    val diceFaces = arrayOf(R.drawable.dice_1, R.drawable.dice_2, R.drawable.dice_3,
        R.drawable.dice_4, R.drawable.dice_5, R.drawable.dice_6)

    private lateinit var btnRoll: Button
    private lateinit var btnAdd: Button
    private lateinit var btnRemove : Button
    private lateinit var tvTotalPoints: TextView
    private lateinit var imgvDiceLeft: ImageView
    private lateinit var imgvDiceCenter: ImageView
    private lateinit var imgvDiceRight: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // get views ids
        btnRoll = findViewById(R.id.btnRoll)
        btnAdd = findViewById(R.id.btnAddDice)
        btnRemove = findViewById(R.id.btnRemoveDice)

        tvTotalPoints = findViewById(R.id.tvTotalPoints)

        imgvDiceLeft = findViewById(R.id.imgvDiceLeft)
        imgvDiceCenter = findViewById(R.id.imgvDiceCenter)
        imgvDiceRight = findViewById(R.id.imgvDiceRight)

        // img array
        val imagesArr = arrayOf(imgvDiceLeft, imgvDiceCenter, imgvDiceRight)

        // set first dice to be active
        dicesArray.first().isActive = true

        btnRoll.setOnClickListener {

            for((index, dice) in dicesArray.withIndex()) {
                if(dice.isActive) {
                    var randomValue = randomizeDice()
                    imagesArr[index].setImageResource(diceFaces[randomValue - 1])
                    dice.points = randomValue
                }
            }

            var sum = dicesArray.filter { it.isActive }.sumOf { it.points }

            tvTotalPoints.setText("TOTAL: $sum")
        }

        btnAdd.setOnClickListener {
            val activeDices = getActiveDices()
            if(activeDices < 3) {
                var inactiveIndex = dicesArray.indexOfFirst { dice -> !dice.isActive }
                imagesArr[inactiveIndex].visibility = View.VISIBLE
                dicesArray[inactiveIndex].isActive = true
            }
        }

        btnRemove.setOnClickListener {
            val activeDices = getActiveDices()
            if(activeDices > 1) {
                var lastActiveIndex = dicesArray.indexOfLast { dice -> dice.isActive }
                imagesArr[lastActiveIndex].visibility = View.GONE
                dicesArray[lastActiveIndex].isActive = false
            }
        }
    }

    private fun randomizeDice(): Int {
        return (1..6).random()
    }

    private fun getActiveDices(): Int {
        return dicesArray.count{ it.isActive }
    }
}

