package commands

import commands.sub.AddTodoCommand
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
    optionListHeading = "\nOption list:%n", // %n or \n
    commandListHeading = "\nSubCommands are:%n", // %n or \n
    subcommands = [
        AddTodoCommand::class
    ],
    subcommandsRepeatable = true
)
object TodoCommand : Callable<Int> {

    const val SUCCESS: Int = 0
    const val FAILURE: Int = 1

    @JvmStatic
    fun main(args: Array<String>) {
        val exitStatus = CommandLine(TodoCommand).execute(
            "add",
            "-m=This is test 1",
            "-m=This is test 2",
            "-m=This is test 3",
            "--create-date=2021-08-01",
            "add",
            "-m=This is test 1",
            "-m=This is test 2",
            "-m=This is test 3",
            "--create-date=2021-08-01"
        )
        exitProcess(exitStatus)
    }

    override fun call(): Int {
        println("[todo] Welcome to Todo")
        return SUCCESS
    }


}