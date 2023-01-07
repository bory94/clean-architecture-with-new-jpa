package com.bory.tutorial.cleanarchitecture.common

import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class RestControllerAdvice {
    @ExceptionHandler(value = [Exception::class])
    fun globalExceptionHandler(e: Exception) {
        
    }
}