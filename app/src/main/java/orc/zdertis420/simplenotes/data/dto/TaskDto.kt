package orc.zdertis420.simplenotes.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import orc.zdertis420.simplenotes.domain.entity.TaskState

@Parcelize
data class TaskDto(
    val name: String,
    val category: String,
    val description: String,
    val id: Long,
    val status: TaskState,
    val timestamp: Long,
) : Parcelable
