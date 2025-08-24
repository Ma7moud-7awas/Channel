package com.m7.channel.data.remote

import io.ktor.client.HttpClient

interface KtorClient {

    val httpClient: HttpClient
}