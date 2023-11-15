package com.example.bboxnewstask.ui.fragments.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bboxnewstask.models.Article
import com.example.bboxnewstask.network.ApiState
import com.example.bboxnewstask.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HeadlinesViewModel  @Inject constructor(private val newsRepository: NewsRepository): ViewModel() {

    private val headlinesFlowMutable: MutableStateFlow<ApiState> = MutableStateFlow(ApiState.Empty)

    val headlineStateFlow: StateFlow<ApiState> = headlinesFlowMutable

    var currentArticle : Article? = null
    fun getHeadlines(source: String) = viewModelScope.launch(Dispatchers.Default) {
        newsRepository.getHeadlines(source)
            .onStart {
            }
            .catch { e ->

            }.collect { exchangeRates ->
                headlinesFlowMutable.value = ApiState.Success(exchangeRates)
            }


    }



}