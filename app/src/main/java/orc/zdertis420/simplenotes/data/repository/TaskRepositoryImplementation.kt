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

    override fun saveActiveTasks(activeTasks: List<TaskDto>) {
        tasksPreferences.edit {
            putString("ACTIVE", Json.encodeToString(activeTasks))
        }
    }

    override fun saveCompletedTasks(completedTasks: List<TaskDto>) {
        tasksPreferences.edit {
            putString("COMPLETED", Json.encodeToString(completedTasks))
        }
    }

    override fun loadActiveTasks(): List<TaskDto> {
        return Json.decodeFromString(tasksPreferences.getString("ACTIVE", "[]").toString())
    }

    override fun loadCompletedTasks(): List<TaskDto> {
        return Json.decodeFromString(tasksPreferences.getString("COMPLETED", "[]").toString())
    }


}