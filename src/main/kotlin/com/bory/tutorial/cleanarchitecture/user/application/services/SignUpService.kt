package com.bory.tutorial.cleanarchitecture.user.application.services

import com.bory.tutorial.cleanarchitecture.user.application.ports.`in`.SignUpCommand
import com.bory.tutorial.cleanarchitecture.user.application.ports.out.GenericUserOutCommands
import com.bory.tutorial.cleanarchitecture.user.application.ports.out.UserQueryByEmail
import com.bory.tutorial.cleanarchitecture.user.domain.SignUpVo
import com.bory.tutorial.cleanarchitecture.user.domain.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SignUpService(
    private val genericUserOutCommands: GenericUserOutCommands,
    private val userQueryByEmail: UserQueryByEmail,
    private val passwordEncoder: PasswordEncoder
) : SignUpCommand {
    override fun signUp(signUpVo: SignUpVo): User {
        when {
            signUpVo.password != signUpVo.passwordConfirm -> throw IllegalArgumentException("Passwords not match")
            userQueryByEmail.existsByEmail(signUpVo.email) ->
                throw java.lang.IllegalArgumentException("Email already exists")
        }

        return genericUserOutCommands.create(signUpVo.createUser(passwordEncoder))
    }
}