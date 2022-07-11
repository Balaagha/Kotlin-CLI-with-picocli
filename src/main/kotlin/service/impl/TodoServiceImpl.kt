package service.impl

import org.mapdb.DB
import org.mapdb.DBMaker.fileDB
import org.mapdb.HTreeMap
import org.mapdb.Serializer
import service.TodoService
import service.model.Status
import service.model.Todo
import java.util.*
import kotlin.collections.ArrayList


class TodoServiceImpl : TodoService {


    companion object {
        const val TODO_MAPDB = "todo.mapdb"
        const val TODO = "todo"
    }

    private lateinit var db: DB

    private lateinit var map: HTreeMap<Long, Any>

    var todoComparator = Comparator<Todo> { o1, o2 ->
        o1.createdOn.compareTo(o2.createdOn)
    }


    private fun start() {
        db = fileDB(TODO_MAPDB).make()
        map = db.hashMap(TODO, Serializer.LONG, Serializer.JAVA).createOrOpen()
    }

    private fun shutdown() {
        db.close()
    }

    private fun operationOnDb(
        task: Todo,
        operationType: OperationType = OperationType.ADD,
        block: ((task: Todo) -> Todo)? = null
    ) {
        start()
        val executedTask = block?.invoke(task) ?: task
        when (operationType) {
            OperationType.ADD -> {
                map[executedTask.id] = executedTask
            }
            OperationType.DELETE -> {
                map.remove(executedTask.id)
            }
        }
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
                block = { mTask ->
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
        val byId = findById(taskId)

        return if (byId?.isPresent == true) {
            message?.let { mMessage ->
                val task = byId.get().apply {
                    this.message = mMessage
                }
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
        val byId = findById(taskId)

        return if (byId?.isPresent == true) {
            status?.let { mStatus ->
                val task = byId.get().apply {
                    this.status = mStatus
                }
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
        return if (task?.isPresent == true) {
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
        return true
    }

    override fun findAll(): List<Todo?>? {
        start()
        val taskList: ArrayList<Todo> = arrayListOf()

        map.values.stream().forEach{
            if (it is Todo){
                taskList.add(it)
            }
        }
        shutdown()
        return taskList
    }

    override fun findByIds(ids: List<Long?>?): List<Todo?>? {
        return null
    }

    override fun findByStatus(status: Status?): List<Todo?>? {
        return null
    }

    override fun findByStatus(statuses: List<Status?>?): List<Todo?>? {
        return null
    }

    override fun findById(id: Long?): Optional<Todo?>? {
        return null
    }

    enum class OperationType {
        ADD, DELETE
    }

}

