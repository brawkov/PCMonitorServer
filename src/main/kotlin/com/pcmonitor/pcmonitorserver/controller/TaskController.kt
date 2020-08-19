package com.pcmonitor.pcmonitorserver.controller


import JwtAuthTokenFilter
import com.pcmonitor.pcmonitorserver.ResponseMessage
import com.pcmonitor.pcmonitorserver.controller.jwt.JwtProvider
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import com.pcmonitor.pcmonitorserver.model.TaskModel
import com.pcmonitor.pcmonitorserver.repository.TaskRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api")
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
            return ResponseEntity(ResponseMessage("BAD_REQUEST"),
                    HttpStatus.BAD_REQUEST)
        }

        val listTask: List<TaskModel> = taskRepository.findByTaskPCId(taskPCId)

        return ResponseEntity.ok(listTask)
    }


}
