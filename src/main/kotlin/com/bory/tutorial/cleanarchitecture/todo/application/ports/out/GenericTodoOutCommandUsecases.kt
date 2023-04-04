package com.bory.tutorial.cleanarchitecture.todo.application.ports.out

import com.bory.tutorial.cleanarchitecture.todo.domain.Todo
import java.util.*

interface GenericTodoOutCommandUsecases {
    fun create(todo: Todo): Todo
    fun modify(uuid: UUID, todo: Todo): Todo
    fun delete(uuid: UUID): Todo
    fun toggleDone(uuid: UUID): Todo
}