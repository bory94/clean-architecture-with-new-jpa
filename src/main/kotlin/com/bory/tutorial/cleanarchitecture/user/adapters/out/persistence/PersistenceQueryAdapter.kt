package com.bory.tutorial.cleanarchitecture.user.adapters.out.persistence

import com.bory.tutorial.cleanarchitecture.exception.ResourceNotFoundException
import com.bory.tutorial.cleanarchitecture.user.application.ports.out.GenericUserOutQueryUsecases
import com.bory.tutorial.cleanarchitecture.user.application.ports.out.QueryByEmailUsecase
import com.bory.tutorial.cleanarchitecture.user.domain.User
import org.springframework.stereotype.Component
import java.util.*

@Component
class PersistenceQueryAdapter(
    private val userRepository: UserRepository,
    private val userMapper: UserMapper
) : GenericUserOutQueryUsecases, QueryByEmailUsecase {
    override fun findAll(): List<User> = userRepository.findAll().map { userMapper.toDomain(it) }

    override fun findOne(uuid: UUID): User =
        userRepository.findById(uuid).map { userMapper.toDomain(it) }
            .orElseThrow { ResourceNotFoundException("User[$uuid] not found") }

    override fun existsByEmail(email: String): Boolean = userRepository.existsByEmail(email)
    override fun findByEmail(email: String): User? = userRepository.findByEmail(email).let {
        userMapper.toDomain(it ?: throw ResourceNotFoundException("User[$email] not found"))
    }
}