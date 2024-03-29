package com.bory.tutorial.cleanarchitecture.common.config

import com.bory.tutorial.cleanarchitecture.user.domain.User
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
@EnableJpaAuditing
class AuditConfig {
    @Bean
    fun currentAuditor(): AuditorAware<UUID> = AuditorAware {
        when (val principal = SecurityContextHolder.getContext().authentication.principal) {
            is User -> Optional.of(principal.uuid)
            else -> Optional.of(UUID.randomUUID())
        }
    }
}