package com.dev.acharyaprashant.core.exception
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ServerResponseException
import java.io.IOException

fun Exception.toCustomExceptions() : ApiException {
    return when (this) {
        is ClientRequestException -> {
            when (this.response.status.value) {
                400 -> ApiException.BadRequestException("The request hostname is invalid",this.response.status.value)
                401 -> ApiException.UnauthorizedException("The request requires user authentication",this.response.status.value)
                403 -> ApiException.ForbiddenException("The server understood the request, but is refusing to fulfill it",this.response.status.value)
                404 -> ApiException.NotFoundException("The server has not found anything matching the Request-URI",this.response.status.value)
                422 -> ApiException.UnknownException("The provided credentials are incorrect.",this.response.status.value)
                else ->ApiException.UnknownException("Something went wrong !",this.response.status.value)
            }
        }
        is ServerResponseException ->ApiException.InternalServerException("Unable to connect to the server side",0)
        is IOException -> ApiException.NoInternetException("Make sure you have an active data connection")
        else -> ApiException.UnknownException("Something went wrong !",0)
    }
}

/*
fun Exception.toCustomExceptions() = when (this) {
    is ServerResponseException -> Failure.HttpErrorInternalServerError(this)
    is ClientRequestException ->
        when (this.response.status.value) {
            400 -> Failure.HttpErrorBadRequest(this)
            401 -> Failure.HttpErrorUnauthorized(this)
            403 -> Failure.HttpErrorForbidden(this)
            404 -> Failure.HttpErrorNotFound(this)
            else -> Failure.HttpError(this)
        }
    is RedirectResponseException -> Failure.HttpError(this)
    else -> Failure.GenericError(this)
}
 */