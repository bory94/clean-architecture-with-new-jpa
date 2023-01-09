package com.bory.tutorial.cleanarchitecture.todo.adapters.out.persistence

import com.bory.tutorial.cleanarchitecture.common.exception.ResourceNotFoundException
import com.bory.tutorial.cleanarchitecture.todo.application.ports.out.GenericTodoOutCommands
import com.bory.tutorial.cleanarchitecture.todo.domain.Todo
import org.springframework.stereotype.Component
import java.util.*

@Component
class TodoPersistenceCommandAdapter(
    private val todoRepository: TodoRepository,
    private val todoMapper: TodoMapper
) : GenericTodoOutCommands {
    override fun create(todo: Todo): Todo {
        LOGGER.debug("Creating a new Todo: $todo")
        val savedTodoEntity = todoRepository.save(
            todoMapper.mapToEntity(todo)
        )

        return todoMapper.mapToDomain(savedTodoEntity)
    }

    override fun modify(uuid: UUID, todo: Todo): Todo {
        LOGGER.debug("Modifying the Todo[$uuid]: $todo")
        val savedTodo = findTodoByIdOrThrow(uuid)
        todoMapper.copyToEntity(todo, savedTodo)

        return todoMapper.mapToDomain(savedTodo)
    }

    override fun delete(uuid: UUID): Todo {
        LOGGER.debug("Deleting the Todo[$uuid]")

        val saved = findTodoByIdOrThrow(uuid)
        todoRepository.delete(saved)

        return todoMapper.mapToDomain(saved)
    }

    override fun toggleDone(uuid: UUID): Todo {
        LOGGER.debug("toggle done of the Todo[$uuid]")
        val savedTodo = findTodoByIdOrThrow(uuid)
        val todo = todoMapper.mapToDomain(savedTodo)

        todo.toggleDone()
        todoMapper.copyToEntity(todo, savedTodo)

        return todoMapper.mapToDomain(savedTodo)
    }

    private fun findTodoByIdOrThrow(uuid: UUID): TodoEntity =
        todoRepository.findById(uuid)
            .orElseThrow { ResourceNotFoundException("Todo[$uuid] not found") }
}