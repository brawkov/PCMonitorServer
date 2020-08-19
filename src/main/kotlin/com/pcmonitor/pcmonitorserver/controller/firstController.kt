package com.pcmonitor.pcmonitorserver.controller


import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import com.pcmonitor.pcmonitorserver.repository.PCGroupRepository
import com.pcmonitor.pcmonitorserver.model.PCGroupModel
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*


@RestController
class HelloController {

    @Autowired
    lateinit var GroupRepository: PCGroupRepository

    @RequestMapping(value = ["/hello"], method = [(RequestMethod.GET)])
    fun getHelloWordMessage(): ResponseEntity<String> =
            ResponseEntity.ok("Hello World")

}
