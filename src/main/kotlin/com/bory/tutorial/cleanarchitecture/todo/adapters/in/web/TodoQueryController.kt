package com.bory.tutorial.cleanarchitecture.todo.adapters.`in`.web

import com.bory.tutorial.cleanarchitecture.todo.application.ports.`in`.TodoInQueryPorts
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/todos")
class TodoQueryController(
    private val todoInQueryPorts: TodoInQueryPorts
) {
    @GetMapping
    fun findAllTodos() = todoInQueryPorts.findAll()

    @GetMapping("/{uuid}")
    fun findOneTodo(@PathVariable("uuid") uuid: UUID) = todoInQueryPorts.findOne(uuid)
}