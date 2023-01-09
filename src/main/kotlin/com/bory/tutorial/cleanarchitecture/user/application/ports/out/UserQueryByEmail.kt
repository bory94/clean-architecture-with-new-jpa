package com.bory.tutorial.cleanarchitecture.user.application.ports.out

import com.bory.tutorial.cleanarchitecture.user.domain.User

interface UserQueryByEmail {
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): User?
}