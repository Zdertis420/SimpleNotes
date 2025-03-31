package orc.zdertis420.simplenotes.domain.entity


data class Task(
    val name: String,
    val id: Long,
    val description: String,
    val completed: Boolean,
    val timestamp: Long
)
