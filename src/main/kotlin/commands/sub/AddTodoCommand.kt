package commands.sub

import picocli.CommandLine
import service.TodoFactory.service
import service.TodoService
import service.model.Todo
import java.util.*
import java.util.concurrent.Callable
import kotlin.system.exitProcess


@CommandLine.Command(
    name = "add",
    aliases = ["create", "plus"],
    version = ["1.0.0"],
    mixinStandardHelpOptions = true,
    requiredOptionMarker = '*',
    description = ["This is a Sub Command to 'todo' and add the task to the list"],
    header = ["Add Todo SubCommand"],
    footerHeading = "%nCopyright",
    footer = ["%nDevelopment by Balaagha Alihumatov"],
    optionListHeading = "\nOptions are:%n", // %n or \n
)
object AddTodoCommand : Callable<Int> {

    @CommandLine.Option(names = ["-m", "--message"], required = true, description = ["a Todo Note or a Message"])
    lateinit var message: Array<String>

    @CommandLine.Option(names = ["--create-date"], required = false, description = ["Created date for the Todo[s]"])
    var createdDate: Date? = null

    private var todoService: TodoService? = null

    private const val SUCCESS: Int = 0

    @JvmStatic
    fun main(args: Array<String>) {
        val exitStatus = CommandLine(AddTodoCommand).execute("--help")
        exitProcess(exitStatus)
    }

    init {
        todoService = service
    }

    override fun call(): Int {
        if (createdDate == null) {
            message.forEach { todoMessage ->
                val todo: Todo? = todoService?.createTodo(todoMessage)
                println("New Task ID is " + todo?.id)
            }
        } else {
            message.forEach { todoMessage ->
                val todo: Todo? = todoService!!.createTodo(todoMessage, createdDate)
                println("New Task ID is " + todo?.id)
            }
        }
        return SUCCESS
    }


}