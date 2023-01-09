package com.bory.tutorial.cleanarchitecture.todo.application.services

import com.bory.tutorial.cleanarchitecture.todo.application.ports.`in`.GenericTodoInCommands
import com.bory.tutorial.cleanarchitecture.todo.application.ports.`in`.GenericTodoInQueries
import com.bory.tutorial.cleanarchitecture.todo.application.ports.out.GenericTodoOutCommands
import com.bory.tutorial.cleanarchitecture.todo.application.ports.out.GenericTodoOutQueries
import com.bory.tutorial.cleanarchitecture.todo.domain.Todo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class TodoService(
    private val genericTodoOutQueries: GenericTodoOutQueries,
    private val genericTodoOutCommands: GenericTodoOutCommands
) : GenericTodoInCommands, GenericTodoInQueries {
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun findAll(): List<Todo> = genericTodoOutQueries.findAll()

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun findOne(uuid: UUID): Todo = genericTodoOutQueries.findOne(uuid)

    override fun create(todo: Todo): Todo = genericTodoOutCommands.create(todo)

    override fun modify(uuid: UUID, todo: Todo): Todo = genericTodoOutCommands.modify(uuid, todo)

    override fun delete(uuid: UUID): Todo = genericTodoOutCommands.delete(uuid)

    override fun toggleDone(uuid: UUID): Todo = genericTodoOutCommands.toggleDone(uuid)
}