package service.impl

import org.mapdb.DB
import org.mapdb.DBMaker.fileDB
import org.mapdb.Serializer
import service.TodoService
import service.model.Status
import service.model.Todo
import java.util.*
import java.util.concurrent.ConcurrentMap


class TodoServiceImpl : TodoService {


    companion object {
        const val TODO_MAPDB = "todo.mapdb"
        const val TODO = "todo"
    }

    private lateinit var db: DB
    private lateinit var map: ConcurrentMap<Long, Any>

    private fun start() {
        db = fileDB(TODO_MAPDB).make()
        map = db.hashMap(TODO, Serializer.LONG, Serializer.JAVA).createOrOpen()
    }

    private fun shutdown() {
        db.close()
    }

    private fun operationOnDb(
        task: Todo? = null,
        operationType: OperationType = OperationType.ADD,
        taskExecutorBlock: ((task: Todo) -> Todo)? = null,
        block: (() -> Unit)? = null
    ) {
        start()
        task?.let { mTask ->
            val executedTask = taskExecutorBlock?.invoke(mTask) ?: mTask
            when (operationType) {
                OperationType.ADD -> {
                    map[executedTask.id] = executedTask
                }
                OperationType.DELETE -> {
                    map.remove(executedTask.id)
                }
            }
        }
        block?.invoke()
        shutdown()
    }

    private fun getNewTaskId(): Long {
        val id = db.atomicLong("id").createOrOpen()
        return id.addAndGet(1)
    }

    override fun createTodo(todoMessage: String?, createdDate: Date?): Todo? {
        return todoMessage?.let { mTodoMessage ->
            val todo = Todo(mTodoMessage)
            operationOnDb(
                task = todo,
                taskExecutorBlock = { mTask ->
                    mTask.id = getNewTaskId()
                    createdDate?.let { mTask.createdOn = it }
                    mTask
                }
            )
            return todo
        } ?: kotlin.run {
            null
        }
    }

    override fun updateMessage(taskId: Long?, message: String?): Todo? {
        val task = findById(taskId)

        return if (task != null) {
            message?.let { mMessage ->
                task.message = mMessage
                operationOnDb(task = task)
                task
            } ?: run {
                null
            }
        } else {
            null
        }
    }

    override fun updateStatus(taskId: Long?, status: Status?): Todo? {
        val task = findById(taskId)

        return if (task != null) {
            status?.let { mStatus ->
                task.status = mStatus
                operationOnDb(task = task)
                task
            } ?: run {
                null
            }
        } else {
            null
        }
    }

    override fun markCompletedById(id: Long?): Boolean {
        val task = findById(id)

        return if (task != null) {
            val updatedTodo = updateStatus(id, Status.COMPLETED)
            Objects.nonNull(updatedTodo)
        } else {
            false
        }
    }

    override fun deleteTodo(todo: Todo?): Boolean {
        return if (todo != null && Objects.nonNull(todo.id)) {
            operationOnDb(
                task = todo,
                operationType = OperationType.DELETE
            )
            true
        } else {
            false
        }
    }

    override fun deleteById(id: Long?): Boolean {
        return if (id != null) {
            operationOnDb(
                task = Todo(id = id),
                operationType = OperationType.DELETE
            )
            true
        } else {
            false
        }
    }

    override fun findAll(): List<Todo?> {
        val taskList: ArrayList<Todo> = arrayListOf()
        operationOnDb {
            map.values.stream().forEach {
                if (it is Todo) {
                    taskList.add(it)
                }
            }
        }
        return taskList.sortedWith(compareBy { it.createdOn })
    }
    override fun findByIds(ids: List<Long?>?): List<Todo?> {
        val taskList: ArrayList<Todo> = arrayListOf()
        operationOnDb {
            ids?.stream()?.map { map[it] }?.forEach {
                if (it is Todo) {
                    taskList.add(it)
                }
            }
        }
        return taskList
    }

    override fun findByStatus(status: Status?): List<Todo?> {
        val todoList: ArrayList<Todo> = ArrayList()

        findAll().forEach { todo ->
            if (todo != null && todo.status === status) {
                todoList.add(todo)
            }
        }

        return todoList

    }

    override fun findByStatus(statuses: List<Status?>?): List<Todo?> {
        val todoList: ArrayList<Todo> = ArrayList()
        findAll().forEach { todo ->
            if (todo != null && statuses?.contains(todo.status) == true) {
                todoList.add(todo)
            }
        }
        return todoList
    }

    override fun findById(id: Long?): Todo? {
        var task: Todo? = null
        operationOnDb {
            id?.let { taskId ->
                task = (map[taskId] as? Todo)
            }
        }
        return task
    }

    enum class OperationType {
        ADD, DELETE
    }

}

