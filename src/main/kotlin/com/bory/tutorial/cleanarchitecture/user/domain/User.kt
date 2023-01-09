package com.bory.tutorial.cleanarchitecture.user.domain

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty
import org.hibernate.validator.constraints.Length
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime
import java.util.*

class User(
    val uuid: UUID = UUID.randomUUID(),
    var name: String? = null,
    var email: String? = null,
    private var password: String? = null,
    var createdBy: Long? = 0,
    var createdTime: LocalDateTime? = LocalDateTime.now(),
    var modifiedBy: Long? = 0,
    var modifiedTime: LocalDateTime? = LocalDateTime.now()
) : UserDetails {
    fun encodePassword(passwordEncoder: PasswordEncoder) = apply {
        password = passwordEncoder.encode(password)
    }

    fun removePassword() = apply {
        password = null
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority("USER"))

    override fun getPassword(): String = password ?: ""

    override fun getUsername(): String = email ?: ""

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}

data class SignInVo(
    @Email
    val email: String,
    @NotEmpty @Length(min = 8)
    val password: String
)

data class SignUpVo(
    @NotEmpty @Length(min = 3)
    val name: String,
    @Email
    val email: String,
    @NotEmpty @Length(min = 8)
    val password: String,
    @NotEmpty @Length(min = 8)
    val passwordConfirm: String
) {
    fun createUser(passwordEncoder: PasswordEncoder): User = User(
        name = name, email = email, password = passwordEncoder.encode(password)
    )
}