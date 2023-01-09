package com.bory.tutorial.cleanarchitecture.user.adapters.out.persistence

import com.bory.tutorial.cleanarchitecture.exception.ResourceNotFoundException
import com.bory.tutorial.cleanarchitecture.user.application.ports.out.GenericUserOutQueries
import com.bory.tutorial.cleanarchitecture.user.application.ports.out.UserQueryByEmail
import com.bory.tutorial.cleanarchitecture.user.domain.User
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserPersistenceQueryAdapter(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) : GenericUserOutQueries, UserQueryByEmail {
    override fun findAll(): List<User> = userRepository.findAll().map { userMapper.toDomain(it) }

    override fun findOne(uuid: UUID): User =
        userRepository.findById(uuid).map { userMapper.toDomain(it) }
            .orElseThrow { ResourceNotFoundException("User[$uuid] not found") }

    override fun existsByEmail(email: String): Boolean = userRepository.existsByEmail(email)
    override fun findByEmail(email: String): User? = userRepository.findByEmail(email).let {
        userMapper.toDomain(it ?: throw ResourceNotFoundException("User[$email] not found"))
    }
}