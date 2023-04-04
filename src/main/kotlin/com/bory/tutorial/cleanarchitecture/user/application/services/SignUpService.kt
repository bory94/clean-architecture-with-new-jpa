package com.bory.tutorial.cleanarchitecture.user.application.services

import com.bory.tutorial.cleanarchitecture.user.application.ports.`in`.SignUpCommandUsecase
import com.bory.tutorial.cleanarchitecture.user.application.ports.out.GenericUserOutCommandUsecases
import com.bory.tutorial.cleanarchitecture.user.application.ports.out.QueryByEmailUsecase
import com.bory.tutorial.cleanarchitecture.user.domain.SignUpDto
import com.bory.tutorial.cleanarchitecture.user.domain.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SignUpService(
    private val genericUserOutCommands: GenericUserOutCommandUsecases,
    private val queryByEmail: QueryByEmailUsecase,
    private val passwordEncoder: PasswordEncoder
) : SignUpCommandUsecase {
    override fun signUp(signUpDto: SignUpDto): User {
        when {
            signUpDto.password != signUpDto.passwordConfirm -> throw IllegalArgumentException("Passwords not match")
            queryByEmail.existsByEmail(signUpDto.email) ->
                throw java.lang.IllegalArgumentException("Email already exists")
        }

        val encodedPassword = passwordEncoder.encode(signUpDto.password)
        return genericUserOutCommands.create(signUpDto.createUser(encodedPassword))
    }
}