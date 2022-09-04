package com.shakircam.quizapp.model

data class Question(
    val id: Int,
    val question : String,
    val option_one : String,
    val option_two : String,
    val option_three : String,
    val option_four : String,
    val right_answer : Int,
)