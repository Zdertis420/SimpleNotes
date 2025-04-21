package orc.zdertis420.simplenotes.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.util.Log
import orc.zdertis420.simplenotes.domain.repository.TaskRepository
import androidx.core.content.edit
import kotlinx.serialization.json.Json
import orc.zdertis420.simplenotes.data.dto.TaskDto

class TaskRepositoryImplementation(context: Context) : TaskRepository {

    private val tasksPreferences by lazy { context.getSharedPreferences("TASKS", MODE_PRIVATE) }

    override fun saveTasks(tasks: List<TaskDto>) {
        tasksPreferences.edit {
            putString("TASKS_LIST", Json.encodeToString(tasks))
        }

        Log.d("SAVE TASK", "repo")
    }

    override fun loadTasks(): List<TaskDto> {
        return Json.decodeFromString<List<TaskDto>>(
            tasksPreferences.getString("TASKS_LIST", "[]").toString()
        )
    }
}