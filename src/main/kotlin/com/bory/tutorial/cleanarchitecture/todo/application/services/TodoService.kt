package com.bory.tutorial.cleanarchitecture.todo.application.services

import com.bory.tutorial.cleanarchitecture.todo.application.ports.`in`.GenericTodoInCommandUsecases
import com.bory.tutorial.cleanarchitecture.todo.application.ports.`in`.GenericTodoInQueryUsecases
import com.bory.tutorial.cleanarchitecture.todo.application.ports.out.GenericTodoOutCommandUsecases
import com.bory.tutorial.cleanarchitecture.todo.application.ports.out.GenericTodoOutQueryUsecases
import com.bory.tutorial.cleanarchitecture.todo.application.ports.out.ToggleTodoUsecase
import com.bory.tutorial.cleanarchitecture.todo.domain.Todo
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class TodoService(
    private val genericTodoOutQueries: GenericTodoOutQueryUsecases,
    private val genericTodoOutCommands: GenericTodoOutCommandUsecases,
    private val toggleTodoUsecase: ToggleTodoUsecase
) : GenericTodoInCommandUsecases, GenericTodoInQueryUsecases {
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun findAll(): List<Todo> = genericTodoOutQueries.findAll()

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    override fun findOne(uuid: UUID): Todo = genericTodoOutQueries.findOne(uuid)

    override fun create(todo: Todo): Todo = genericTodoOutCommands.create(todo)

    override fun modify(uuid: UUID, todo: Todo): Todo = genericTodoOutCommands.modify(uuid, todo)

    override fun delete(uuid: UUID): Todo = genericTodoOutCommands.delete(uuid)

    override fun toggleDone(uuid: UUID): Todo = toggleTodoUsecase.toggleDone(uuid)
}