package service

import service.impl.TodoServiceImpl

object TodoFactory {
    val service: TodoService
        get() = TodoServiceImpl()
}