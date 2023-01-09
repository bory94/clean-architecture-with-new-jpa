package com.bory.tutorial.cleanarchitecture.common.service

import com.bory.tutorial.cleanarchitecture.user.application.ports.out.UserQueryByEmail
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class JwtUserDetailsService(
    private val userQueryByEmail: UserQueryByEmail
) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        userQueryByEmail.findByEmail(username)
            ?: throw UsernameNotFoundException("User[$username] not found")
}