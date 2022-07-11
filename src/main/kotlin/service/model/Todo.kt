package service.model

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

//data class Todo (
//    @SerializedName("id")
//    val id: Long,
//    @SerializedName("message")
//    val message: String,
//    @SerializedName("createdOn")
//    val createdOn: Date = Date(),
//    @SerializedName("modifiedOn")
//    val modifiedOn: Date = Date(),
//    val status: Status = Status.CREATED
//) {
//    override fun toString(): String {
//        val simpleDateFormat = SimpleDateFormat("YYYY/MMM/YY HH:MM")
//        return "Todo{" +
//                "id=$id" +
//                ", message=" + message + '\'' +
//                ", status=$status" +
//                ", createdOn=${simpleDateFormat.format(createdOn)}"+
//                ", modifiedOn=${simpleDateFormat.format(modifiedOn)}"+
//                '}'
//    }
//}
//

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