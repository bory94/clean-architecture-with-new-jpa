package com.bory.tutorial.cleanarchitecture.user.application.ports.out

import com.bory.tutorial.cleanarchitecture.user.domain.User
import java.util.*

interface GenericUserOutQueries {
    fun findAll(): List<User>
    fun findOne(uuid: UUID): User
}