package com.m7.channel.data.repo

import com.m7.channel.data.model.SearchSection
import com.m7.channel.data.model.Section
import com.m7.channel.data.remote.SectionRemoteDS
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SectionRepo @Inject constructor(
    private val sectionRemoteDS: SectionRemoteDS,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {

    suspend fun getHomeSections(): List<Section> {
        return withContext(dispatcher) {
            sectionRemoteDS.getHomeSections().sections
        }
    }

    suspend fun searchSections(query: String): List<SearchSection> {
        return withContext(dispatcher) {
            sectionRemoteDS.searchAsSections(query).sections
        }
    }
}
