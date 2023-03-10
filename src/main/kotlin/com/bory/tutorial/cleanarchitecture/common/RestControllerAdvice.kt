package com.bory.tutorial.cleanarchitecture.common

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestControllerAdvice {
    @ExceptionHandler(value = [Exception::class])
    fun globalExceptionHandler(e: Exception) = ResponseEntity.internalServerError().body(e.message)

    @ExceptionHandler(value = [IllegalArgumentException::class])
    fun illegalArgumentExceptionHandler(e: IllegalArgumentException) =
        ResponseEntity.badRequest().body(e.message)

}