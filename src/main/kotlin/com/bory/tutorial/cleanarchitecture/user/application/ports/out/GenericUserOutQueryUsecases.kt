package com.bory.tutorial.cleanarchitecture.user.application.ports.out

import com.bory.tutorial.cleanarchitecture.user.domain.User
import java.util.*

interface GenericUserOutQueryUsecases {
    fun findAll(): List<User>
    fun findOne(uuid: UUID): User
}