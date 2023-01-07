package com.bory.tutorial.cleanarchitecture.todo.adapters.`in`.web

import com.bory.tutorial.cleanarchitecture.todo.application.ports.`in`.TodoInCommands
import com.bory.tutorial.cleanarchitecture.todo.domain.Todo
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/todos")
class TodoCommandController(
    private val todoInCommands: TodoInCommands,
) {
    @PostMapping
    fun createTodo(@RequestBody @Valid todo: Todo) = todoInCommands.create(todo).let {
        LOGGER.debug("Todo Created: $it")
        ResponseEntity.created(URI("/todos/${todo.uuid}")).build<String>()
    }

    @PutMapping("/{uuid}")
    fun modifyTodo(@PathVariable("uuid") uuid: UUID, @RequestBody @Valid todo: Todo) =
        todoInCommands.modify(uuid, todo).let {
            LOGGER.debug("Todo Modified: $it")
            ResponseEntity.ok().build<String>()
        }

    @PatchMapping("/{uuid}/toggle-done")
    fun toggleDone(@PathVariable("uuid") uuid: UUID) = todoInCommands.toggleDone(uuid).let {
        LOGGER.debug("Todo Done toggled: $it")
        ResponseEntity.ok().build<String>()
    }

    @DeleteMapping("/{uuid}")
    fun deleteTodo(@PathVariable("uuid") uuid: UUID) = todoInCommands.delete(uuid).let {
        LOGGER.debug("Todo Deleted: $it")
        ResponseEntity.accepted().build<String>()
    }
}