package com.dev.acharyaprashant.core.exception

sealed class ApiException:Exception() {
    class BadRequestException(val info: String,val statusCode: Int) : ApiException()
    class UnauthorizedException(val info: String,val statusCode: Int) : ApiException()
    class ForbiddenException(val info: String,val statusCode: Int) : ApiException()
    class NotFoundException(val info: String,val statusCode: Int) : ApiException()
    class InternalServerException(val info: String,val statusCode: Int): ApiException()
    class UnknownException(val info: String,val statusCode: Int): ApiException()
    class NoInternetException(val info: String):ApiException()
}