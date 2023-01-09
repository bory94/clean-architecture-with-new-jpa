package com.bory.tutorial.cleanarchitecture.user.application.services

import com.bory.tutorial.cleanarchitecture.common.exception.ResourceAlreadyExistsException
import com.bory.tutorial.cleanarchitecture.user.application.ports.`in`.GenericUserInCommands
import com.bory.tutorial.cleanarchitecture.user.application.ports.`in`.GenericUserInQueries
import com.bory.tutorial.cleanarchitecture.user.application.ports.out.GenericUserOutCommands
import com.bory.tutorial.cleanarchitecture.user.application.ports.out.GenericUserOutQueries
import com.bory.tutorial.cleanarchitecture.user.application.ports.out.UserQueryByEmail
import com.bory.tutorial.cleanarchitecture.user.domain.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class UserService(
    private val genericUserOutQueries: GenericUserOutQueries,
    private val userQueryByEmail: UserQueryByEmail,
    private val genericUserOutCommands: GenericUserOutCommands,
    private val passwordEncoder: PasswordEncoder
) : GenericUserInCommands, GenericUserInQueries {
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun findAll(): List<User> = genericUserOutQueries.findAll()

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun findOne(uuid: UUID): User = genericUserOutQueries.findOne(uuid)

    override fun create(user: User): User {
        if (userQueryByEmail.existsByEmail(
                user.email ?: throw IllegalArgumentException("Email cannot be null")
            )
        ) {
            throw ResourceAlreadyExistsException("User[$user.email] already exists")
        }

        return user.encodePassword(passwordEncoder).apply(genericUserOutCommands::create)
    }

    override fun modify(uuid: UUID, user: User): User = genericUserOutCommands.modify(uuid, user)

    override fun delete(uuid: UUID): User = genericUserOutCommands.delete(uuid)
}