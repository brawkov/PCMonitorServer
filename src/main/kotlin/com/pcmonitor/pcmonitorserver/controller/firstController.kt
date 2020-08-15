package com.pcmonitor.pcmonitorserver.controller

import com.pcmonitor.pcmonitorserver.repository.PersonRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController
import org.springframework.beans.factory.annotation.Autowired

import com.pcmonitor.pcmonitorserver.repository.UserRepository


@RestController
class HelloController {
    @RequestMapping(value = ["/hello"], method = [(RequestMethod.GET)])
    fun getHelloWordMessage(): ResponseEntity<String> =
            ResponseEntity.ok("Hello World")

    @Autowired
    lateinit var personRepository: PersonRepository

    @GetMapping("/persons")
    fun getPersons() = personRepository.findAll()
}
