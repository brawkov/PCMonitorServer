package com.pcmonitor.pcmonitorserver.controllers


import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

import com.pcmonitor.pcmonitorserver.repositories.PCGroupRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity


@RestController
class HelloController {

    @Autowired
    lateinit var GroupRepository: PCGroupRepository

    @RequestMapping(value = ["/hello"], method = [(RequestMethod.GET)])
    fun getHelloWordMessage(): ResponseEntity<String> =
            ResponseEntity.ok("Hello World")

}
