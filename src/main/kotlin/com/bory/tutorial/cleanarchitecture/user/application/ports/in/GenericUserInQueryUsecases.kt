package com.bory.tutorial.cleanarchitecture.user.application.ports.`in`

import com.bory.tutorial.cleanarchitecture.user.domain.User
import java.util.*

interface GenericUserInQueryUsecases {
    fun findAll(): List<User>
    fun findOne(uuid: UUID): User
}