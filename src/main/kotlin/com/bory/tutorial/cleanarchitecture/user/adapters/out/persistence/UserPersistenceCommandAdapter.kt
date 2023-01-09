package com.bory.tutorial.cleanarchitecture.user.adapters.out.persistence

import com.bory.tutorial.cleanarchitecture.common.exception.ResourceNotFoundException
import com.bory.tutorial.cleanarchitecture.user.application.ports.out.GenericUserOutCommands
import com.bory.tutorial.cleanarchitecture.user.domain.User
import org.springframework.stereotype.Component
import java.util.*

@Component
class UserPersistenceCommandAdapter(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) : GenericUserOutCommands {
    override fun create(user: User): User {
        val savedEntity = userRepository.save(userMapper.toEntity(user))

        return userMapper.toDomain(savedEntity)
    }

    override fun modify(uuid: UUID, user: User): User {
        val savedEntity = findByIdOrThrow(uuid)

        userMapper.copyToEntity(user, savedEntity)

        return userMapper.toDomain(savedEntity)
    }

    override fun delete(uuid: UUID): User {
        val savedEntity = findByIdOrThrow(uuid)

        userRepository.delete(savedEntity)

        return userMapper.toDomain(savedEntity)
    }

    private fun findByIdOrThrow(uuid: UUID) =
        userRepository.findById(uuid)
            .orElseThrow { ResourceNotFoundException("User[$uuid] not found") }
}