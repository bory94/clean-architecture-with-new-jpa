package com.bory.tutorial.cleanarchitecture.user.application.ports.`in`

import com.bory.tutorial.cleanarchitecture.user.domain.SignUpVo
import com.bory.tutorial.cleanarchitecture.user.domain.User

interface SignUpCommand {
    fun signUp(signUpVo: SignUpVo): User
}