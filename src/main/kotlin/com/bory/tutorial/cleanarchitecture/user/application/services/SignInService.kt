package com.bory.tutorial.cleanarchitecture.user.application.services

import com.bory.tutorial.cleanarchitecture.common.LOGGER
import com.bory.tutorial.cleanarchitecture.exception.ResourceNotFoundException
import com.bory.tutorial.cleanarchitecture.common.service.JwtService
import com.bory.tutorial.cleanarchitecture.user.application.ports.`in`.SignInCommand
import com.bory.tutorial.cleanarchitecture.user.application.ports.out.UserQueryByEmail
import com.bory.tutorial.cleanarchitecture.user.domain.SignInVo
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SignInService(
    private val authenticationManager: AuthenticationManager,
    private val userQueryByEmail: UserQueryByEmail,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) : SignInCommand {
    override fun signIn(signInVo: SignInVo): String {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                signInVo.email,
                signInVo.password
            )
        )

        LOGGER.debug("authentication.name: ${authentication.name}")
        LOGGER.debug("authentication.principal: ${authentication.principal}")
        LOGGER.debug("authentication.authorities: ${authentication.authorities}")
        LOGGER.debug("authentication.details: ${authentication.details}")

        val savedUser = userQueryByEmail.findByEmail(signInVo.email)
            ?: throw ResourceNotFoundException("User[${signInVo.email} not found")

        if (!passwordEncoder.matches(signInVo.password, savedUser.password)) {
            throw ResourceNotFoundException("User[${signInVo.email} not found")
        }

        return jwtService.generateToken(savedUser)
    }
}