import commands.HelloCommand
import picocli.CommandLine


object App {
    @JvmStatic
    fun main(args: Array<String>) {
        CommandLine(HelloCommand).execute(*args)
    }
}


