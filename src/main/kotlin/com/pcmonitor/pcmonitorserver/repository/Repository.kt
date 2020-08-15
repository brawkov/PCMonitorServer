package com.pcmonitor.pcmonitorserver.repository


import com.pcmonitor.pcmonitorserver.model.Person
import org.springframework.stereotype.Repository
import org.springframework.data.repository.CrudRepository

@Repository
interface PersonRepository: CrudRepository<Person, Long> {}

