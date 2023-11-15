package com.example.bboxnewstask.repository

import com.example.bboxnewstask.models.Headlines
import com.example.bboxnewstask.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository
@Inject
constructor(private val apiService: ApiService) {

    fun getHeadlines(source: String): Flow<Headlines> = flow {
        emit(apiService.getHeadlines(source))
    }.flowOn(Dispatchers.IO)

}



