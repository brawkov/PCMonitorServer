package com.pcmonitor.pcmonitorserver.controllers



import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import com.pcmonitor.pcmonitorserver.models.PCModel
import com.pcmonitor.pcmonitorserver.repositories.PCRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
@CrossOrigin(origins = ["*"], maxAge = 3600)
@RequestMapping("/api")
@PreAuthorize("hasRole('ROLE_USER')")
class PCController {

    @Autowired
    lateinit var pcRepository: PCRepository


    @GetMapping("/pc")
    fun getPcById(@RequestParam paramRequest: Map<String, String>): ResponseEntity<*> {
        try {
            val pcId: Long = (paramRequest.get("pcId")!!.toLong())
            val PC: PCModel = pcRepository.findByPcId(pcId)
//            if(PC.isPresent)
            return ResponseEntity.ok(PC)
        }
        catch (e: Exception) {
            return ResponseEntity(mapOf("message" to "Устройство не найдено"),
                        HttpStatus.NOT_FOUND)
        }
    }

    fun getListPcByPcGroupId(pcGroupId: Long): List<PCRepository.PcIdAndNameProjection> {
        val listPC: List<PCRepository.PcIdAndNameProjection> = pcRepository.findByPcGroupId(pcGroupId)
        return listPC
    }
}
