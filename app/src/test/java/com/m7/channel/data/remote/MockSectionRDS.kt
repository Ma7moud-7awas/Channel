package com.m7.channel.data.remote

import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel

val mockHomeSectionsResponseJson = """
{
  "sections": [
    {
      "name": "w",
      "type": "square",
      "content_type": "podcast",
      "order": 0,
      "content": [
        {
          "podcast_id": "6dc7d195-1719-4669-8af6-f6b0ab374181",
          "name": "Licensed Fresh Ball",
          "description": "The beautiful range of Apple Naturalé that has an exciting mix of natural ingredients. With the Goodness of 100% Natural Ingredients",
          "avatar_url": "https://avatars.githubusercontent.com/u/29308535",
          "episode_count": 40,
          "duration": 26488,
          "language": "in commodo",
          "priority": 1,
          "popularityScore": 18,
          "score": 18
        },
        {
          "podcast_id": "a93dfab0-9554-4e76-9595-710e758c777a",
          "name": "Handmade Cotton Hat",
          "description": "The slim & simple Maple Gaming Keyboard from Dev Byte comes with a sleek body and 7- Color RGB LED Back-lighting for smart functionality",
          "avatar_url": "https://avatars.githubusercontent.com/u/89313700",
          "episode_count": 84,
          "duration": 12344,
          "language": "elit pariatur",
          "priority": 1,
          "popularityScore": 22,
          "score": 22
        }
      ]
    }
  ]
}
""".trimIndent()
val mockHomeSectionsEmptyResponseJson = """
{
  "sections": []
}
""".trimIndent()

enum class ResponseState(val response: String) {
    SUCCESS(mockHomeSectionsResponseJson),
    EMPTY(mockHomeSectionsEmptyResponseJson),
    ERROR("")
}

var responseState = ResponseState.SUCCESS
var statusCode = HttpStatusCode.OK

val mockEngine = MockEngine { request ->
    when (request.url.encodedPath) {
        "/home_sections" -> {
            respond(
                content = ByteReadChannel(responseState.response.toByteArray(Charsets.UTF_8)),
                status = statusCode,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        else -> respond(
            content = ByteReadChannel(
                "${request.url.encodedPath} Page Not Found"
                    .toByteArray(Charsets.UTF_8)
            ),
            status = HttpStatusCode.NotFound,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }
}
