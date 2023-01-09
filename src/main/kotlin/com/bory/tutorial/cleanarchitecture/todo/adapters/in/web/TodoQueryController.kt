package com.bory.tutorial.cleanarchitecture.todo.adapters.`in`.web

import com.bory.tutorial.cleanarchitecture.todo.application.ports.`in`.GenericTodoInQueries
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/public/v1/todos")
class TodoQueryController(
    private val genericTodoInQueries: GenericTodoInQueries
) {
    @GetMapping
    fun findAllTodos() = genericTodoInQueries.findAll()

    @GetMapping("/{uuid}")
    fun findOneTodo(@PathVariable("uuid") uuid: UUID) = genericTodoInQueries.findOne(uuid)
}