package orc.zdertis420.simplenotes.data.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class TaskDto(
    val name: String,
    val category: String,
    val description: String,
    val id: Int,
    val completed: Boolean,
    val timestamp: Long,
) : Parcelable
