package com.pcmonitor.pcmonitorserver.repository

import java.util.Optional
import com.pcmonitor.pcmonitorserver.model.TaskModel
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository: JpaRepository<TaskModel, Long> {
    fun findByTaskId(@Param("taskId") taskId: Long): Optional<TaskModel>

    fun findByTaskPCId(@Param("taskPCId") taskPCId: Int): List<TaskModel>
}