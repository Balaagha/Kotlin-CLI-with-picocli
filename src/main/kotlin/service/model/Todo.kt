package service.model

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*


class Todo : Serializable {
    var id: Long? = null
    var message: String
    var status = Status.CREATED
    var createdOn = Date()
    var modifiedOn = Date()

    constructor(message: String) {
        this.message = message
    }

    constructor(message: String, createdOn: Date) {
        this.message = message
        this.createdOn = createdOn
    }

    override fun toString(): String {
        val simpleDateFormat = SimpleDateFormat("YYYY/MMM/DD HH:MM")
        return "Todo{" +
                "id=" + id +
                ", message='" + message + '\'' +
                ", status=" + status +
                ", createdOn=" + simpleDateFormat.format(createdOn) +
                ", modifiedOn=" + simpleDateFormat.format(modifiedOn) +
                '}'
    }
}