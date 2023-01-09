package com.bory.tutorial.cleanarchitecture.user.adapters.out.persistence

import com.bory.tutorial.cleanarchitecture.common.AbstractEntity
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "user_account")
class UserEntity(
    @Id
    val uuid: UUID = UUID.randomUUID(),
    var name: String? = null,
    var email: String? = null,
    var password: String? = null
) : AbstractEntity()