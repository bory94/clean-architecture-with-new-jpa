package com.bory.tutorial.cleanarchitecture.todo.adapters.out.persistence

import com.bory.tutorial.cleanarchitecture.common.AbstractEntity
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "todo")
class TodoEntity(
    @Id
    var uuid: UUID,
    var title: String,
    var done: Boolean
) : AbstractEntity() {
}