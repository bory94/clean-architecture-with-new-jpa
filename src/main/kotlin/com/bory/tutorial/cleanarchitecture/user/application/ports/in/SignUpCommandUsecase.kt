package com.bory.tutorial.cleanarchitecture.user.application.ports.`in`

import com.bory.tutorial.cleanarchitecture.user.domain.SignUpVo
import com.bory.tutorial.cleanarchitecture.user.domain.User

interface SignUpCommandUsecase {
    fun signUp(signUpVo: SignUpVo): User
}