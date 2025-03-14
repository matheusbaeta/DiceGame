package com.example.dicegame

import android.widget.ImageView

data class Dice(
    var isActive: Boolean = false,
    var currentFace: Int = R.drawable.dice_1,
    var points: Int = 1
)
