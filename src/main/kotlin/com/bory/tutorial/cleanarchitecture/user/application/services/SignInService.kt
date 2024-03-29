package com.bory.tutorial.cleanarchitecture.user.application.services

import com.bory.tutorial.cleanarchitecture.common.LOGGER
import com.bory.tutorial.cleanarchitecture.common.service.JwtService
import com.bory.tutorial.cleanarchitecture.exception.ResourceNotFoundException
import com.bory.tutorial.cleanarchitecture.user.application.ports.`in`.SignInCommandUsecase
import com.bory.tutorial.cleanarchitecture.user.application.ports.out.QueryByEmailUsecase
import com.bory.tutorial.cleanarchitecture.user.domain.SignInDto
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class SignInService(
    private val authenticationManager: AuthenticationManager,
    private val queryByEmail: QueryByEmailUsecase,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) : SignInCommandUsecase {
    override fun signIn(signInDto: SignInDto): String {
        val authentication = authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                signInDto.email,
                signInDto.password
            )
        )

        LOGGER.debug("authentication.name: {}", authentication.name)
        LOGGER.debug("authentication.principal: {}", authentication.principal)
        LOGGER.debug("authentication.authorities: {}", authentication.authorities)
        LOGGER.debug("authentication.details: {}", authentication.details)

        val savedUser = queryByEmail.findByEmail(authentication.name)
            ?: throw ResourceNotFoundException("User[${authentication.name} not found")

        if (!passwordEncoder.matches(signInDto.password, savedUser.password)) {
            throw ResourceNotFoundException("User[${authentication.name} not found")
        }

        return jwtService.generateToken(savedUser)
    }
}