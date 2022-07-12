package service

import service.TodoFactory.service
import service.model.Status
import service.model.Todo
import java.util.function.Consumer

object DatabaseApplication {

    @JvmStatic
    fun main(args: Array<String>) {
        val service = service

        val clearDatabase = true

        if (clearDatabase) {
            System.err.println("Clearing Database")
            service.findAll().forEach(Consumer { todo: Todo? ->
                service.deleteTodo(
                    todo
                )
            })
        }

        val helloCommandTask = service.createTodo("Create Hello Command")
        val todoCommandTask = service.createTodo("Create Todo Command")
        val addCommandTask = service.createTodo("Create Add Command")
        val listCommandTask = service.createTodo("Create List Command")
        val serviceTask = service.createTodo("Create Service for Storing data")

        println("Printing All Tasks")
        service.findAll().forEach(Consumer { x: Todo? -> println(x) })

        service.updateStatus(helloCommandTask?.id, Status.COMPLETED)
        service.updateStatus(todoCommandTask?.id, Status.IN_PROGRESS)
        service.updateStatus(addCommandTask?.id, Status.IN_PROGRESS)
        service.updateStatus(listCommandTask?.id, Status.IN_PROGRESS)
        service.updateStatus(serviceTask?.id, Status.COMPLETED)

        println("Printing All In Progress Tasks")
        service.findByStatus(Status.IN_PROGRESS).forEach(Consumer { x: Todo? ->
            println(
                x
            )
        })

        println("\n\n-- Dummy Task --")
        val dummyTask = service.createTodo("Dummy Task to be deleted")
        println("dummyTask = $dummyTask")
        System.out.println(
            "service.findById(dummyTask?.id) = " + service.findById(dummyTask?.id)?.message
        )
        println(
            "service.updateMessage(dummyTask?.id, \"Updated Dummy Task Message\") = " + service.updateMessage(
                dummyTask?.id,
                "Updated Dummy Task Message"
            )!!.message
        )
        println(
            "service.updateStatus(dummyTask?.id, Status.IN_PROGRESS) = " + service.updateStatus(
                dummyTask?.id,
                Status.IN_PROGRESS
            )!!.status
        )
        println("service.markCompletedById(dummyTask?.id) = " + service.markCompletedById(dummyTask?.id))
        System.out.println(
            "service.findById(dummyTask?.id) = " + service.findById(dummyTask?.id)?.status
        )
        println("service.deleteById(dummyTask?.id) = " + service.deleteById(dummyTask?.id))


    }
}