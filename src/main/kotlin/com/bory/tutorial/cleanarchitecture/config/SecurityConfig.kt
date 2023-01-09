package com.bory.tutorial.cleanarchitecture.config

import com.bory.tutorial.cleanarchitecture.common.service.JwtUserDetailsService
import com.bory.tutorial.cleanarchitecture.config.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

/*
If you want to use /h2-console with Spring Security, Look around this blog post.
https://github.com/HomoEfficio/dev-tips/blob/master/Spring%20Security%EC%99%80%20h2-console%20%ED%95%A8%EA%BB%98%20%EC%93%B0%EA%B8%B0.md
*/
@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter,
    private val jwtUserDetailsService: JwtUserDetailsService
) {
    @Bean
    fun httpSecurity(http: HttpSecurity): SecurityFilterChain =
        http.csrf().disable()
            .formLogin().disable()
            .exceptionHandling()
            .and()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers("/public/**").permitAll()
                    .anyRequest().authenticated()
            }
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(
                jwtAuthenticationFilter,
                UsernamePasswordAuthenticationFilter::class.java
            )
            .build()

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()

    @Bean
    fun authenticationProvider(): AuthenticationProvider = DaoAuthenticationProvider().apply {
        setUserDetailsService(jwtUserDetailsService)
        setPasswordEncoder(passwordEncoder())
    }

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager


}