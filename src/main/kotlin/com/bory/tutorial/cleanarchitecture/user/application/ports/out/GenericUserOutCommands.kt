package com.bory.tutorial.cleanarchitecture.user.application.ports.out

import com.bory.tutorial.cleanarchitecture.user.domain.User
import java.util.*

interface GenericUserOutCommands {
    fun create(user: User): User
    fun modify(uuid: UUID, user: User): User
    fun delete(uuid: UUID): User
}