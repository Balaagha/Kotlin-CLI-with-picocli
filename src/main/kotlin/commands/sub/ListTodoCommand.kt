package commands.sub

import commands.utils.ListFormat
import picocli.CommandLine
import service.TodoFactory.service
import service.TodoService
import service.model.Status
import service.model.Todo
import java.text.SimpleDateFormat
import java.util.concurrent.Callable
import kotlin.system.exitProcess


@CommandLine.Command(name = "list",
    aliases = ["ls", "show"],
    version = ["1.0.0"],
    mixinStandardHelpOptions = true,
    requiredOptionMarker = '*',
    description = ["This a Sub Command and lists all the tasks as per the parameters"],
    header = ["List Todo SubCommand"],
    optionListHeading = "%nOptions are:%n",
    footerHeading = "%nCopyright",
    footer = ["%nDevelopment by Balaagha Alihumatov"]
)

object ListTodoCommand : Callable<Int> {

    private const val SUCCESS: Int = 0

    @CommandLine.Option(
        names = ["-f", "--format"],
        description = ["Formatting the Todo and the default value is \${DEFAULT-VALUE} %nAll Formats are \${COMPLETION-CANDIDATES}"],
        defaultValue = "DEFAULT",
        required = false
    )
    var format: ListFormat? = null

    @CommandLine.Option(
        names = ["-s", "--status"],
        description = ["Lists the todo by Status %nAvailable Statuses are \${COMPLETION-CANDIDATES}"],
        required = false
    )
    var status: Status? = null

    @CommandLine.Option(
        names = ["--short", "--compact"],
        description = ["Lists the todo in SHORT format"],
        required = false
    )
    var compact = false

    @CommandLine.Option(
        names = ["--completed", "--done"],
        description = ["Lists the todo which are either completed or not completed"],
        negatable = true,
        required = false
    )
    var completed: Boolean? = null

    @CommandLine.Option(
        names = ["--id"],
        description = ["Shows the todos for the given ID"],
        required = false,
        split = ","
    )
    var id: Array<Long>? = null

    var todoService: TodoService? = null
    var dateFormat = SimpleDateFormat("yyyy-mm-dd")

    init {
        todoService = service
    }

    @JvmStatic
    fun main(args: Array<String>) {
        val exitStatus = CommandLine(ListTodoCommand).execute("--help")
        exitProcess(exitStatus)
    }

    override fun call(): Int {

        if (compact) {
            format = ListFormat.SHORT
        }

        val todoList: List<Todo?> = if (id != null) {
            todoService!!.findByIds(id!!.toList())
        } else if (completed != null) {
            if (status != null) {
                todoService!!.findAll()
            } else {
                todoService!!.findByStatus(status)
            }
        } else {
            val statuses: MutableList<Status?> = ArrayList()
            if (completed!!) {
                statuses.add(Status.COMPLETED)
            } else {
                statuses.add(Status.CREATED)
                statuses.add(Status.IN_PROGRESS)
            }
            todoService!!.findByStatus(statuses)
        }

        if (todoList.isNotEmpty()) {
            todoList.stream().forEach { todo: Todo? -> printTodo(format!!, todo!!) }
        } else {
            println("No Tasks to display !!!")
        }

        return SUCCESS
    }

    private fun printTodo(format: ListFormat, todo: Todo) {
        if (format === ListFormat.SHORT) {
            println(
                java.lang.String.format(
                    "%4d %3s %10s %s",
                    todo.id,
                    getStatus(todo),
                    dateFormat.format(todo.createdOn),
                    todo.message
                )
            )
        } else {
            println("ID      = " + todo.id)
            println("Message = " + todo.message)
            println("Status  = " + todo.status)
            println("Created = " + todo.createdOn)
        }
    }

    private fun getStatus(todo: Todo): String {
        return when(todo.status){
            Status.COMPLETED -> "[x]"
            Status.IN_PROGRESS -> "[/]"
            else -> "[ ]"
        }
    }


}