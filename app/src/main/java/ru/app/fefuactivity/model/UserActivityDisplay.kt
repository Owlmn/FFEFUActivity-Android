package ru.app.fefuactivity.model

data class UserActivityDisplay(
    val distance: String,
    val username: String,
    val time: String,
    val type: String,
    val date: String,
    val duration: String? = null,
    val start: String? = null,
    val finish: String? = null,
    val comment: String? = null
) 