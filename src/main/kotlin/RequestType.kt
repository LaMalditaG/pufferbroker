package io.github.lamalditag

import io.ktor.http.*

/**
 * Basic enum class to categorize all the different valid requests this broker can make, the url to make the request to, and the type of http request that should be used
 */
enum class RequestType(val request: String, val method: HttpMethod) {
	START("start", HttpMethod.Post),
	STOP("stop", HttpMethod.Post),
	DELETE("", HttpMethod.Delete),
	STATUS("status", HttpMethod.Get),
	//CREATE("", HttpMethod.Post),
}