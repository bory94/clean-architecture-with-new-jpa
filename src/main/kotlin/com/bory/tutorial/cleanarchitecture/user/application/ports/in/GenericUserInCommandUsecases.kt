package com.bory.tutorial.cleanarchitecture.user.application.ports.`in`

import com.bory.tutorial.cleanarchitecture.user.domain.User
import java.util.*

interface GenericUserInCommandUsecases {
    fun create(user: User): User
    fun modify(uuid: UUID, user: User): User
    fun delete(uuid: UUID): User
}