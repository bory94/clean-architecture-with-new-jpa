package com.bory.tutorial.cleanarchitecture.user.application.services

import com.bory.tutorial.cleanarchitecture.exception.ResourceAlreadyExistsException
import com.bory.tutorial.cleanarchitecture.user.application.ports.`in`.GenericUserInCommandUsecases
import com.bory.tutorial.cleanarchitecture.user.application.ports.`in`.GenericUserInQueryUsecases
import com.bory.tutorial.cleanarchitecture.user.application.ports.out.GenericUserOutCommandUsecases
import com.bory.tutorial.cleanarchitecture.user.application.ports.out.GenericUserOutQueryUsecases
import com.bory.tutorial.cleanarchitecture.user.application.ports.out.UserQueryByEmailUsecase
import com.bory.tutorial.cleanarchitecture.user.domain.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class UserService(
    private val genericUserOutQueries: GenericUserOutQueryUsecases,
    private val userQueryByEmail: UserQueryByEmailUsecase,
    private val genericUserOutCommands: GenericUserOutCommandUsecases,
    private val passwordEncoder: PasswordEncoder
) : GenericUserInCommandUsecases, GenericUserInQueryUsecases {
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