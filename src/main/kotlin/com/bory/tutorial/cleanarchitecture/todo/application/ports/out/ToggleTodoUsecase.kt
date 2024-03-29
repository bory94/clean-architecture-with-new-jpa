package com.bory.tutorial.cleanarchitecture.todo.application.ports.out

import com.bory.tutorial.cleanarchitecture.todo.domain.Todo
import java.util.*

interface ToggleTodoUsecase {
    fun toggleDone(uuid: UUID): Todo
}