package com.m7.channel.data.remote

import com.m7.channel.data.model.SearchRowObject
import com.m7.channel.data.model.SectionsRowObject
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

class SectionRemoteDS @Inject constructor(
    private val client: Client
) {

    suspend fun getHomeSections(): SectionsRowObject {
        return client.httpClient
            .get("https://api-v2-b2sit6oh3a-uc.a.run.app/home_sections")
            .body()
    }

    suspend fun searchAsSections(query: String): SearchRowObject {
        return client.httpClient
            .get("https://mock.apidog.com/m1/735111-711675-default/search?") {
                parameter("name", query)
            }
            .body()
    }
}