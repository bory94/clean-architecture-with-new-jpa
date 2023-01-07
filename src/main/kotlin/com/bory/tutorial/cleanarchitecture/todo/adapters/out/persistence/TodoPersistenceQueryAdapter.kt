package com.bory.tutorial.cleanarchitecture.todo.adapters.out.persistence

import com.bory.tutorial.cleanarchitecture.todo.adapters.out.persistence.exception.ResourceNotFoundException
import com.bory.tutorial.cleanarchitecture.todo.application.ports.out.TodoOutQueryPorts
import com.bory.tutorial.cleanarchitecture.todo.domain.Todo
import org.springframework.stereotype.Component
import java.util.*

@Component
class TodoPersistenceQueryAdapter(
    private val todoRepository: TodoRepository,
    private val todoMapper: TodoMapper
) : TodoOutQueryPorts {
    override fun findAll(): List<Todo> = todoRepository.findAll().map(todoMapper::mapToDomain)

    override fun findOne(uuid: UUID): Todo =
        findTodoByIdOrThrow(uuid)
            .let {
                todoMapper.mapToDomain(it)
            }

    private fun findTodoByIdOrThrow(uuid: UUID): TodoEntity =
        todoRepository.findById(uuid)
            .orElseThrow { ResourceNotFoundException("Todo[$uuid] not found") }
}