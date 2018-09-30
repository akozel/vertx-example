package by.akozel.consensus.core.config.response

import java.time.Instant

object ResponseMessageLibrary {

    val RESOURCE_NOT_FOUND_404 = ResponseStatus("Resource not found")
    val RESOURCE_BAD_REQUEST_400 = ResponseStatus("Bad request")
    val RESOURCE_UNAUTHORIZED_401 = ResponseStatus("Unauthorized")

    fun customResponseMessage(message: String) = ResponseStatus(message)

}

class ResponseStatus (val message: String, val timestamp: Instant = Instant.now())