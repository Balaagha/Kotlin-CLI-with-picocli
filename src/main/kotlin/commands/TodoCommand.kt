package commands

import commands.sub.AddTodoCommand
import commands.sub.ListTodoCommand
import picocli.CommandLine
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "Todo",
    version = ["1.0.0"],
    mixinStandardHelpOptions = true,
    requiredOptionMarker = '*',
    description = ["This is a todo Tool which will help us to manage todo activities"],
    header = ["Todo CLI"],
    footerHeading = "%nCopyright",
    footer = ["%nDevelopment by Balaagha Alihumatov"],
    optionListHeading = "\nOptions are:%n", // %n or \n
    commandListHeading = "\nSubCommands are:%n", // %n or \n
    subcommands = [
        AddTodoCommand::class,
        ListTodoCommand::class
    ],
    subcommandsRepeatable = true
)
object TodoCommand : Callable<Int> {

    private const val SUCCESS: Int = 0

    @JvmStatic
    fun main(args: Array<String>) {
        val exitStatus = CommandLine(TodoCommand).execute(
            "add","--message=Test1",
            "add","--message=Test2","--create-date=2021-01-01"
        )
        exitProcess(exitStatus)
    }

    override fun call(): Int {
        println("[todo] Welcome to Todo")
        return SUCCESS
    }


}