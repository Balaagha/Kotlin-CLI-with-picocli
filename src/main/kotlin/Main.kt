import commands.HelloCommand
import picocli.CommandLine
import service.TodoFactory.service


object App {
    @JvmStatic
    fun main(args: Array<String>) {
        // Basic implementation of Command Line
        CommandLine(HelloCommand).execute(*args)
        val service = service

        // Basic implementation for TodoService
        service.createTodo("First task")
        service.createTodo("Second task")
        println("Printing All Tasks");
        service.findAll()?.forEach {
            println(it?.message)
        }

    }
}


