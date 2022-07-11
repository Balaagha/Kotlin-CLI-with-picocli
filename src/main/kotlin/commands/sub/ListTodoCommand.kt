package commands.sub

import picocli.CommandLine
import java.util.concurrent.Callable
import kotlin.system.exitProcess

@CommandLine.Command(
    name = "list",
    aliases = ["ls", "show"],
    version = ["1.0.0"],
    mixinStandardHelpOptions = true,
    requiredOptionMarker = '*',
    description = ["This is a Sub Command to list and show all the task as per the parameters"],
    header = ["List Todo SubCommand"],
    footerHeading = "%nCopyright",
    footer = ["%nDevelopment by Balaagha Alihumatov"],
    optionListHeading = "\nOptions are:%n", // %n or \n
)
object ListTodoCommand : Callable<Int> {

    private const val SUCCESS: Int = 0

    @JvmStatic
    fun main(args: Array<String>) {
        val exitStatus = CommandLine(ListTodoCommand).execute("--help")
        exitProcess(exitStatus)
    }

    override fun call(): Int {
        println("[list] List Command")
        return SUCCESS
    }


}