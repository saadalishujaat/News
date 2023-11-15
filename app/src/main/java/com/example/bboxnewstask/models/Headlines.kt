package com.example.bboxnewstask.models

import kotlinx.serialization.*


@Serializable
data class Headlines (
    val status: String,
    val totalResults: Long,
    val articles: List<Article>
)




