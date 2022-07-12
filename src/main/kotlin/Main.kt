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
        service.createTodo("task7")
        service.createTodo("task8")
        service.createTodo("task9")
        service.createTodo("task10")
        println("Printing All Tasks");
        service.findAll()?.forEach {
            println("message: ${it?.message}, date: ${it?.createdOn}, id: ${it?.id}")
        }

        service.findByIds(listOf(1,2,3,4,6))?.forEach {
            println("message: ${it?.message}, date: ${it?.createdOn}, id: ${it?.id}")
        }

    }
}


