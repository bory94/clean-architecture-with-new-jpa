package com.bory.tutorial.cleanarchitecture.todo.domain

import jakarta.validation.constraints.NotEmpty
import java.time.LocalDateTime
import java.util.*

data class Todo(
    val uuid: UUID = UUID.randomUUID(),
    @NotEmpty
    var title: String,
    var done: Boolean = false,
    var createdBy: UUID? = null,
    var createdTime: LocalDateTime? = LocalDateTime.now(),
    var modifiedBy: UUID? = null,
    var modifiedTime: LocalDateTime? = LocalDateTime.now()
) {
    fun toggleDone() = apply {
        this.done = !this.done
    }
}