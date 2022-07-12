package service.model

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*


data class Todos(
    var id: Long? = null,
    var message: String,
    var status: Status = Status.CREATED,
    var createdOn: Date = Date(),
    var modifiedOn: Date = Date(),
): Serializable {

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