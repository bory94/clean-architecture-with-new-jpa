package com.bory.tutorial.cleanarchitecture.user.application.ports.out

import com.bory.tutorial.cleanarchitecture.user.domain.User

interface QueryByEmailUsecase {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): User?
}