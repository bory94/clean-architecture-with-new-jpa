package com.bory.tutorial.cleanarchitecture.config.filter

import com.bory.tutorial.cleanarchitecture.common.LOGGER
import com.bory.tutorial.cleanarchitecture.common.exception.InvalidJwtTokenException
import com.bory.tutorial.cleanarchitecture.common.service.JwtService
import com.bory.tutorial.cleanarchitecture.common.service.JwtUserDetailsService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

private const val BEARER_KEY = "Bearer "
private const val BEARER_KEY_LENGTH = BEARER_KEY.length

@Component
class JwtAuthenticationFilter(
    private val jwtService: JwtService,
    private val jwtUserDetailsService: JwtUserDetailsService
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest, response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token = request.authorizationHeader()
        if (token == null) {
            filterChain.doFilter(request, response)
            return
        }

        val email = jwtService.extractSubject(token)
        if (email != null && SecurityContextHolder.getContext().authentication != null) {
            filterChain.doFilter(request, response)
            return
        }
        if (email == null) {
            filterChain.doFilter(request, response)
            return
        }

        loadUserDetailsAndSetSecurityContext(email, token, request)

        filterChain.doFilter(request, response)
    }

    private fun loadUserDetailsAndSetSecurityContext(
        email: String,
        token: String,
        request: HttpServletRequest
    ) {
        try {
            val foundUser = jwtUserDetailsService.loadUserByUsername(email)
            if (!jwtService.isTokenValid(token, foundUser)) {
                throw InvalidJwtTokenException("Token's email and User email not match")
            }
            val authentication =
                UsernamePasswordAuthenticationToken(foundUser, null, foundUser.authorities)
            authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = authentication
        } catch (rne: UsernameNotFoundException) {
            LOGGER.error("No User loaded by Jwt.", rne)
        } catch (ijte: InvalidJwtTokenException) {
            LOGGER.error("Jwt Token is not valid.", ijte)
        }
    }
}

private fun HttpServletRequest.authorizationHeader(): String? {
    val authHeader = getHeader("Authorization") ?: ""
    if (!authHeader.startsWith(BEARER_KEY)) {
        return null
    }

    return authHeader.substring(BEARER_KEY_LENGTH)
}