package com.bory.tutorial.cleanarchitecture.todo.application.ports.`in`

import com.bory.tutorial.cleanarchitecture.todo.domain.Todo
import java.util.*

interface GenericTodoInQueries {
    fun findAll(): List<Todo>
    fun findOne(uuid: UUID): Todo
}