package orc.zdertis420.simplenotes.data.repository

import android.content.Context
import android.content.Context.MODE_PRIVATE
import orc.zdertis420.simplenotes.domain.entity.Task
import orc.zdertis420.simplenotes.domain.repository.TaskRepository
import androidx.core.content.edit
import kotlinx.serialization.json.Json

class TaskRepositoryImplementation(context: Context) : TaskRepository {

    private val tasksPreferences by lazy { context.getSharedPreferences("TASKS", MODE_PRIVATE) }

    override fun saveTasks(tasks: List<Task>) {
        tasksPreferences.edit {
            putString("TASKS_LIST", Json.encodeToString(tasks))
        }
    }

    override fun loadTasks(callback: (Result<List<Task>>) -> Unit) {
        try {
            val tasks = Json.decodeFromString<List<Task>>(tasksPreferences.getString("TASKS_LIST", "").toString())

            if (tasks.isNotEmpty()) {
                callback(Result.success(tasks))
            } else {
                callback(Result.success(emptyList()))
            }
        } catch (e: Exception) {
            callback(Result.failure(Throwable(e)))
        }
    }
}