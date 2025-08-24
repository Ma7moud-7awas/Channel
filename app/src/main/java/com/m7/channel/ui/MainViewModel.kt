package com.m7.channel.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.m7.channel.data.model.Section
import com.m7.channel.data.repo.SectionRepo
import com.m7.channel.ui.model.SectionsState
import com.m7.channel.ui.model.UISection
import com.m7.channel.ui.model.toUISection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.map

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sectionRepo: SectionRepo
) : ViewModel() {

    private val _homeSections = MutableStateFlow<SectionsState>(SectionsState.Loading)
    val homeSections = _homeSections.asStateFlow()

    init {
        loadHomeSections()
    }

    fun loadHomeSections() {
        viewModelScope.launch {
            _homeSections.value = try {
                sectionRepo.getHomeSections()
                    .map { it.toUISection() }
                    .sortedBy { it.order }
                    .let {
                        SectionsState.Success(it)
                    }
            } catch (e: Exception) {
                SectionsState.Error(e.message)
            }
        }
    }

    private var searchJob: Job? = null

    private val _searchSections =
        MutableStateFlow<SectionsState>(SectionsState.Success(emptyList()))
    val searchSections = _searchSections.asStateFlow()

    fun searchAsSections(query: String) {
        searchJob?.cancel() // cancel previous calls
        searchJob = viewModelScope.launch {
            delay(200)
            _searchSections.value = SectionsState.Loading // display loading on new calls

            _searchSections.value = try {
                sectionRepo.searchSections(query)
                    .map { it.toUISection() }
                    .sortedBy { it.order }
                    .let {
                        SectionsState.Success(it)
                    }
            } catch (e: Exception) {
                SectionsState.Error(e.message)
            }
        }
    }
}
