package com.bory.tutorial.cleanarchitecture.common

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class AbstractEntity(
    @CreatedBy
    var createdBy: Long? = null,
    @CreatedDate
    var createdTime: LocalDateTime? = null,
    @LastModifiedBy
    var modifiedBy: Long? = null,
    @LastModifiedDate
    var modifiedTime: LocalDateTime? = null
) {
}