package commands

import picocli.CommandLine

@CommandLine.Command(
    name = "Hello",
    version = ["1.0.0"],
    mixinStandardHelpOptions = true,
    requiredOptionMarker = '*',
    description = ["This is a simple Hello Command"],
    header = ["Sample Command"],
    optionListHeading = "\nOption list:%n" // %n or \n
)
object HelloCommand : Runnable {

    @JvmStatic
    fun main(args: Array<String>) {
        CommandLine(HelloCommand).execute(*args)
    }

    @CommandLine.Option(
        names = ["-u", "--user"],
        required = false,
        description = ["Provide User Name"],
        paramLabel = "<user name>"
    )
    lateinit var user: String

    override fun run() {
        if (user.isEmpty()) {
            println("[hello] Hello World")
        } else {
            println("[hello] Hello $user")
        }
    }

}