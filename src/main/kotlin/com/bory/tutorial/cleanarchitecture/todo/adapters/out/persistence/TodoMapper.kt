package com.bory.tutorial.cleanarchitecture.todo.adapters.out.persistence

import com.bory.tutorial.cleanarchitecture.todo.domain.Todo
import org.springframework.stereotype.Component

@Component
class TodoMapper {
    fun mapToEntity(todo: Todo): TodoEntity = with(todo) {
        TodoEntity(uuid, title, done)
    }

    fun copyToEntity(todo: Todo, todoEntity: TodoEntity) {
        todoEntity.done = todo.done
        todoEntity.title = todo.title
    }

    fun mapToDomain(todoEntity: TodoEntity) = with(todoEntity) {
        Todo(uuid, title, done, createdBy, createdTime, modifiedBy, modifiedTime)
    }
}