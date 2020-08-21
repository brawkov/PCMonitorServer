package com.pcmonitor.pcmonitorserver.controllers


import com.jayway.jsonpath.internal.JsonContext
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import com.pcmonitor.pcmonitorserver.repositories.PCGroupRepository
import com.pcmonitor.pcmonitorserver.models.PCGroupModel
import com.pcmonitor.pcmonitorserver.models.PCModel
import com.pcmonitor.pcmonitorserver.repositories.PCRepository
import com.pcmonitor.pcmonitorserver.services.CreatePCGroupRequestValidation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.validation.Errors
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api")
@PreAuthorize("hasRole('ROLE_USER')")
class PCGroupController {

    @Autowired
    lateinit var pcGroupRepository: PCGroupRepository

    @Autowired
    lateinit var pcController: PCController

    @Autowired
    private val createPCGroupRequestValidation: CreatePCGroupRequestValidation? = null

    @Autowired
    lateinit var pcRepository: PCRepository

    @GetMapping("/group")
    fun getlistGroup(): ResponseEntity<*> {

        class GroupWithPC(var groupId: Long, var groupName: String, var listrPC: List<PCRepository.PcIdAndNameProjection>)

        val listGroupWithPC: MutableList<GroupWithPC> = mutableListOf()
        val listGroup: List<PCGroupModel> = pcGroupRepository.findAll()

        for (name in listGroup) {
            listGroupWithPC.add(GroupWithPC(name.groupId!!, name.groupName, pcController.getListPcByPcGroupId(name.groupId)))
        }

        return ResponseEntity.ok(listGroupWithPC)
    }


    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.addValidators(createPCGroupRequestValidation)
    }

    class NewPCGropRequest(val groupName: String, val listPC: List<Long>)

    @PostMapping("/group")
    fun createGroup(@RequestBody @Validated newPCGropRequest: NewPCGropRequest, errors: Errors): ResponseEntity<*> {

        val errorMessage: MutableMap<String, String> = mutableMapOf()

        return if (errors.hasErrors()) {
            for (error in errors.fieldErrors) {
                errorMessage += error.code.toString() to error.defaultMessage.toString()
            }
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        } else {
            val pcGroup = PCGroupModel(
                    0,
                    newPCGropRequest.groupName
            )


            pcGroupRepository.save(pcGroup)
            val newPCGrop: Optional<PCGroupModel> = pcGroupRepository.findByGroupName(newPCGropRequest.groupName)
            for (pcId in newPCGropRequest.listPC) {
                val pc: PCModel = pcRepository.findByPcId(pcId)
                pc.pcGroupId = newPCGrop.get().groupId
                pcRepository.save(pc)
            }
            ResponseEntity.ok(mapOf("message" to "Группа создана"))
        }
//        return ResponseEntity.ok(mapOf("message" to "Группа создана"))

    }
}
