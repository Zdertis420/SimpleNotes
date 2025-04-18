package orc.zdertis420.simplenotes.data

import orc.zdertis420.simplenotes.data.dto.TaskDto
import orc.zdertis420.simplenotes.domain.entity.Task

fun Task.toDto(): TaskDto {
    return TaskDto(
        name = name,
        category = category,
        description = description,
        id = id,
        status = status,
        timestamp = timestamp
    )
}

fun TaskDto.toTask(): Task {
    return Task(
        name = name,
        category = category,
        description = description,
        id = id,
        status = status,
        timestamp = timestamp
    )
}