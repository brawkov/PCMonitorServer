package com.pcmonitor.pcmonitorserver.controllers



import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import com.pcmonitor.pcmonitorserver.models.TaskModel
import com.pcmonitor.pcmonitorserver.repositories.TaskRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/api")
@CrossOrigin ("*")
class TaskController {

    @Autowired
    lateinit var taskRepository: TaskRepository

    @GetMapping("/task")
    @PreAuthorize("hasRole('ROLE_USER')")
    fun getListTask(@RequestParam paramRequest: Map<String, String>): ResponseEntity<*> {

        var taskPCId: Int = 1

        try {
            taskPCId = (paramRequest.get("taskPCId")!!.toInt())
        } catch (e: Exception) {
            return ResponseEntity(mapOf("message" to "BAD_REQUEST"),
                    HttpStatus.BAD_REQUEST)
        }

        val listTask: List<TaskModel> = taskRepository.findByTaskPCId(taskPCId)

        return ResponseEntity.ok(listTask)
    }


}
