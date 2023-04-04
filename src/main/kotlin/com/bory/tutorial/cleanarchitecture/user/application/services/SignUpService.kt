package com.bory.tutorial.cleanarchitecture.user.application.services

import com.bory.tutorial.cleanarchitecture.user.application.ports.`in`.SignUpCommandUsecase
import com.bory.tutorial.cleanarchitecture.user.application.ports.out.GenericUserOutCommandUsecases
import com.bory.tutorial.cleanarchitecture.user.application.ports.out.UserQueryByEmailUsecase
import com.bory.tutorial.cleanarchitecture.user.domain.SignUpVo
import com.bory.tutorial.cleanarchitecture.user.domain.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SignUpService(
    private val genericUserOutCommands: GenericUserOutCommandUsecases,
    private val userQueryByEmail: UserQueryByEmailUsecase,
    private val passwordEncoder: PasswordEncoder
) : SignUpCommandUsecase {
    override fun signUp(signUpVo: SignUpVo): User {
        when {
            signUpVo.password != signUpVo.passwordConfirm -> throw IllegalArgumentException("Passwords not match")
            userQueryByEmail.existsByEmail(signUpVo.email) ->
                throw java.lang.IllegalArgumentException("Email already exists")
        }

        val encodedPassword = passwordEncoder.encode(signUpVo.password)
        return genericUserOutCommands.create(signUpVo.createUser(encodedPassword))
    }
}