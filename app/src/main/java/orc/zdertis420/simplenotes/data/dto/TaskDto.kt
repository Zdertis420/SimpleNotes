package orc.zdertis420.simplenotes.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskDto(
    val name: String,
    val description: String,
    val category: String,
    val id: Long,
    val status: TaskStateDto,
    val timestamp: Long,
    val deadline: Long?
) : Parcelable
