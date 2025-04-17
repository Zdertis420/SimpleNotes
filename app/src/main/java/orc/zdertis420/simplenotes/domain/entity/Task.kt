package orc.zdertis420.simplenotes.domain.entity


data class Task(
    val name: String,
    val category: String,
    val description: String,
    val id: Long,
    val status: TaskState,
    val timestamp: Long,
)
