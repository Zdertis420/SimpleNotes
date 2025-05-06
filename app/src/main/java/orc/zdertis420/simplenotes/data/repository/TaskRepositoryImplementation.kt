package orc.zdertis420.simplenotes.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import orc.zdertis420.simplenotes.domain.repository.TaskRepository
import androidx.core.content.edit
import kotlinx.serialization.json.Json
import orc.zdertis420.simplenotes.data.dto.TaskDto
import orc.zdertis420.simplenotes.domain.entity.TaskType

class TaskRepositoryImplementation(context: Context) : TaskRepository {

    private val tasksPreferences by lazy { context.getSharedPreferences("TASKS", MODE_PRIVATE) }

    override fun saveTasks(
        activeTasks: List<TaskDto>,
        completedTasks: List<TaskDto>
    ) {
        tasksPreferences.edit {
            putString("ACTIVE_TASKS", Json.encodeToString(activeTasks))
            putString("COMPLETED_TASKS", Json.encodeToString(completedTasks))
        }
    }

    override fun loadTasks(): Map<TaskType, List<TaskDto>> {
        val activeTasks = Json.decodeFromString<List<TaskDto>>(
            tasksPreferences.getString("ACTIVE_TASKS", "[]").toString()
        )
        val completedTasks = Json.decodeFromString<List<TaskDto>>(
            tasksPreferences.getString("COMPLETED_TASKS", "[]").toString()
        )

        val tasks: Map<TaskType, List<TaskDto>> = mapOf(TaskType.ACTIVE to activeTasks, TaskType.COMPLETED to completedTasks)

        return tasks
    }
}