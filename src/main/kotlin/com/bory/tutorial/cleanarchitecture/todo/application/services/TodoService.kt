package com.bory.tutorial.cleanarchitecture.todo.application.services

import com.bory.tutorial.cleanarchitecture.todo.application.ports.`in`.TodoInCommands
import com.bory.tutorial.cleanarchitecture.todo.application.ports.`in`.TodoInQueryPorts
import com.bory.tutorial.cleanarchitecture.todo.application.ports.out.TodoOutCommands
import com.bory.tutorial.cleanarchitecture.todo.application.ports.out.TodoOutQueryPorts
import com.bory.tutorial.cleanarchitecture.todo.domain.Todo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class TodoService(
    private val todoOutQueryPorts: TodoOutQueryPorts,
    private val todoOutCommands: TodoOutCommands
) : TodoInCommands, TodoInQueryPorts {
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun findAll(): List<Todo> = todoOutQueryPorts.findAll()

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun findOne(uuid: UUID): Todo = todoOutQueryPorts.findOne(uuid)

    override fun create(todo: Todo): Todo = todoOutCommands.create(todo)

    override fun modify(uuid: UUID, todo: Todo): Todo = todoOutCommands.modify(uuid, todo)

    override fun delete(uuid: UUID): Todo = todoOutCommands.delete(uuid)

    override fun toggleDone(uuid: UUID): Todo = todoOutCommands.toggleDone(uuid)
}