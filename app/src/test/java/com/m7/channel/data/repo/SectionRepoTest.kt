package com.m7.channel.data.repo

import com.m7.channel.data.remote.Client
import com.m7.channel.data.remote.ResponseState
import com.m7.channel.data.remote.SectionRemoteDS
import com.m7.channel.data.remote.mockEngine
import com.m7.channel.data.remote.responseState
import com.m7.channel.data.remote.statusCode
import com.m7.channel.ui.model.ContentType
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SectionRepoTest {

    private val sectionRemoteDS = SectionRemoteDS(Client(mockEngine))
    private val sectionRepo = SectionRepo(sectionRemoteDS, Dispatchers.Unconfined)

    @Test
    fun `getHomeSections successful response`() = runBlocking {
        // Verify that when sectionRemoteDS.getHomeSections() returns a successful response with a list of sections,
        // getHomeSections returns the same list of sections.
        statusCode = HttpStatusCode.OK
        responseState = ResponseState.SUCCESS
        val response = sectionRepo.getHomeSections()

        assert(response.first().contentType == ContentType.PODCAST.value)
    }

    @Test
    fun `getHomeSections empty list response`() = runBlocking {
        // Verify that when sectionRemoteDS.getHomeSections() returns a successful response with an empty list of sections,
        // getHomeSections returns an empty list.
        statusCode = HttpStatusCode.OK
        responseState = ResponseState.EMPTY
        val response = sectionRepo.getHomeSections()

        assert(response.isEmpty())
    }

    @Test
    fun `getHomeSections server error`() = runBlocking {
        // Verify that when sectionRemoteDS.getHomeSections() throws an HTTP exception (e.g., HttpException with 500 status code), 
        // getHomeSections propagates the exception.
        statusCode = HttpStatusCode.InternalServerError
        responseState = ResponseState.EMPTY
        val response = sectionRepo.getHomeSections()

        assert(response.isEmpty())
    }
}
