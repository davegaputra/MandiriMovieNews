package com.example.mandirimovienews.view_model

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mandirimovienews.model.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import com.example.mandirimovienews.repository.NewsRepository
import com.example.mandirimovienews.util.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SearchNewsViewModel @Inject constructor(
    private val repository: NewsRepository,
) : BaseViewModel() {
    private lateinit var _newsFlow: Flow<PagingData<Article>>
    val newsFlow: Flow<PagingData<Article>> get() = _newsFlow

    fun getSearchNews(keyword: String) = launchPagingAsync({
        repository.getSearchNews(keyword).cachedIn(viewModelScope)
    }, {
        _newsFlow = it
    })

}