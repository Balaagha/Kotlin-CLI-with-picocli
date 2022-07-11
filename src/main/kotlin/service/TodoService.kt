package service

import service.model.Status
import service.model.Todo
import java.util.*

interface TodoService {
    fun createTodo(todoMessage: String?, createdDate: Date? = null): Todo?

    fun updateMessage(taskId: Long?, message: String?): Todo?

    fun updateStatus(taskId: Long?, status: Status?): Todo?

    fun markCompletedById(id: Long?): Boolean

    fun deleteTodo(todo: Todo?): Boolean

    fun deleteById(id: Long?): Boolean

    fun findAll(): List<Todo?>?

    fun findByIds(ids: List<Long?>?): List<Todo?>?

    fun findByStatus(status: Status?): List<Todo?>?

    fun findById(id: Long?): Optional<Todo?>?

    fun findByStatus(statuses: List<Status?>?): List<Todo?>?
}