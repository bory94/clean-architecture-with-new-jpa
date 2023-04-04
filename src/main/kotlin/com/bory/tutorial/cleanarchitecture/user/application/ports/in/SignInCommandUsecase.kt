package com.bory.tutorial.cleanarchitecture.user.application.ports.`in`

import com.bory.tutorial.cleanarchitecture.user.domain.SignInDto

interface SignInCommandUsecase {
    fun signIn(signInDto: SignInDto): String
}