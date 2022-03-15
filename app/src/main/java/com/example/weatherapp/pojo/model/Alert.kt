package com.example.weatherapp.pojo.model

import java.io.Serializable

data class Alert(
    val description: String,
    val end: Long,
    val event: String,
    val sender_name: String,
    val start: Long,
    val tags: List<String>
) : Serializable