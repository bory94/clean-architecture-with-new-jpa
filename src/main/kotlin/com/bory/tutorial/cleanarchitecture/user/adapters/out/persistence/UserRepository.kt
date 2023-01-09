package com.bory.tutorial.cleanarchitecture.user.adapters.out.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserEntity, UUID> {

    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): UserEntity?

}