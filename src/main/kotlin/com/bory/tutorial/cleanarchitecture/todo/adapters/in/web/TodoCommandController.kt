package com.bory.tutorial.cleanarchitecture.todo.adapters.`in`.web

import com.bory.tutorial.cleanarchitecture.todo.application.ports.`in`.GenericTodoInCommands
import com.bory.tutorial.cleanarchitecture.todo.domain.Todo
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import java.util.*

@RestController
@RequestMapping("/v1/todos")
class TodoCommandController(
    private val genericTodoInCommands: GenericTodoInCommands,
) {
    @PostMapping
    fun createTodo(@RequestBody @Valid todo: Todo) =
        genericTodoInCommands.create(todo).let {
            LOGGER.debug("Todo Created: $it")
            ResponseEntity.created(URI("/todos/${todo.uuid}")).build<Unit>()
        }

    @PutMapping("/{uuid}")
    fun modifyTodo(
        @PathVariable("uuid") uuid: UUID,
        @RequestBody @Valid todo: Todo
    ): ResponseEntity<Unit> =
        genericTodoInCommands.modify(uuid, todo).let {
            LOGGER.debug("Todo Modified: $it")
            ResponseEntity.ok().build()
        }

    @PatchMapping("/{uuid}/toggle-done")
    fun toggleDone(@PathVariable("uuid") uuid: UUID) =
        genericTodoInCommands.toggleDone(uuid).let {
            LOGGER.debug("Todo Done toggled: $it")
            ResponseEntity.ok().build<Unit>()
        }

    @DeleteMapping("/{uuid}")
    fun deleteTodo(@PathVariable("uuid") uuid: UUID) =
        genericTodoInCommands.delete(uuid).let {
            LOGGER.debug("Todo Deleted: $it")
            ResponseEntity.accepted().build<Unit>()
        }
}