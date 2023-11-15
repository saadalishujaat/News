package com.example.bboxnewstask.config


object ConstURL {

    private const val baseUrl = "https://newsapi.org/v2/"
    private const val apiKey = "e4d7584abc81489f881f163b70d78c3e"

    fun getHeadlines(source: String): String {
        return "$baseUrl/top-headlines?sources=$source&apiKey=$apiKey"
    }
}