package com.bory.tutorial.cleanarchitecture.todo.application.ports.out

import com.bory.tutorial.cleanarchitecture.todo.domain.Todo
import java.util.*

interface GenericTodoOutQueryUsecases {
    fun findAll(): List<Todo>
    fun findOne(uuid: UUID): Todo
}