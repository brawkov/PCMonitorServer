package com.pcmonitor.pcmonitorserver.controller


import com.pcmonitor.pcmonitorserver.controller.jwt.JwtProvider
import com.pcmonitor.pcmonitorserver.controller.jwt.JwtResponse
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import com.pcmonitor.pcmonitorserver.repository.PCGroupRepository
import com.pcmonitor.pcmonitorserver.model.PCGroupModel
import com.pcmonitor.pcmonitorserver.repository.PCRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ROLE_USER')")
class PCGroupController {

    @Autowired
    lateinit var GroupRepository: PCGroupRepository

    @Autowired
    lateinit var pcController: PCController


    @GetMapping("/group")
    fun getlistGroup(): ResponseEntity<*> {

        class GroupWithPC(var groupId: Long, var groupName: String, var PC: List<PCRepository.PcIdAndNameProjection>)

        val listGroupWithPC: MutableList<GroupWithPC> = mutableListOf()
        val listGroup: List<PCGroupModel> = GroupRepository.findAll()

        for (name in listGroup) {
            listGroupWithPC.add(GroupWithPC(name.groupId!!, name.groupName!!, pcController.getListPcByPcGroupId(name.groupId)))
        }

        return ResponseEntity.ok(listGroupWithPC)
    }
}
