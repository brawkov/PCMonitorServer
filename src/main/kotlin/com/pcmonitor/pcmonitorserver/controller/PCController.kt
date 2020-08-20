package com.pcmonitor.pcmonitorserver.controller


import com.pcmonitor.pcmonitorserver.ResponseMessage
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import com.pcmonitor.pcmonitorserver.model.PCModel
import com.pcmonitor.pcmonitorserver.repository.PCRepository
import javassist.NotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.crossstore.ChangeSetPersister
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ROLE_USER')")
class PCController {

    @Autowired
    lateinit var pcRepository: PCRepository


    @GetMapping("/pc")
    @CrossOrigin ("*")
    fun getPcById(@RequestParam paramRequest: Map<String, String>): ResponseEntity<*> {
        try {
            val pcId: Long = (paramRequest.get("pcId")!!.toLong())
            val PC: Optional<PCModel> = pcRepository.findByPcId(pcId)
//            if(PC.isPresent)
            return ResponseEntity.ok(PC)
        }
        catch (e: Exception) {
            return ResponseEntity(ResponseMessage("Устройство не найдено"),
                        HttpStatus.NOT_FOUND)
        }
    }

    fun getListPcByPcGroupId(pcGroupId: Long): List<PCRepository.PcIdAndNameProjection> {
        val listPC: List<PCRepository.PcIdAndNameProjection> = pcRepository.findByPcGroupId(pcGroupId)
        return listPC
    }
}
