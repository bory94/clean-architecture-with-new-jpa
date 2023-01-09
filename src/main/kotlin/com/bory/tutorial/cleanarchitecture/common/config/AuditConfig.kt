package com.bory.tutorial.cleanarchitecture.common.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import java.util.*

@Configuration
@EnableJpaAuditing
class AuditConfig {
    @Bean
    fun currentAuditor(): AuditorAware<Long> = AuditorAware { Optional.of(1) }
}