package orc.zdertis420.simplenotes.domain.entity


data class Task(
    val name: String,
    val category: String,
    val description: String,
    val id: Int,
    val completed: Boolean,
    val timestamp: Long,
)
