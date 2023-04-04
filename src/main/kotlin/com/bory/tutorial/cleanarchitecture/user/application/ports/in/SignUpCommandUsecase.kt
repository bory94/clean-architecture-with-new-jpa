package com.bory.tutorial.cleanarchitecture.user.application.ports.`in`

import com.bory.tutorial.cleanarchitecture.user.domain.SignUpDto
import com.bory.tutorial.cleanarchitecture.user.domain.User

interface SignUpCommandUsecase {
    fun signUp(signUpDto: SignUpDto): User
}