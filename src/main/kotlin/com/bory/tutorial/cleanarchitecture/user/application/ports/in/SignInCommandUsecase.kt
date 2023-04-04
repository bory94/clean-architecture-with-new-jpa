package com.bory.tutorial.cleanarchitecture.user.application.ports.`in`

import com.bory.tutorial.cleanarchitecture.user.domain.SignInVo

interface SignInCommandUsecase {
    fun signIn(signInVo: SignInVo): String
}