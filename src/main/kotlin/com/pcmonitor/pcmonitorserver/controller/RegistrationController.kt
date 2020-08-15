package com.pcmonitor.pcmonitorserver.controller


import com.fasterxml.jackson.annotation.JsonProperty
import java.io.Serializable

class RegistrationController : Serializable {

    @JsonProperty("firstName")
    var firstName: String? = null

    @JsonProperty("secondName")
    var secondName: String? = null

    @JsonProperty("lastName")
    var lastName: String? = null

    @JsonProperty("email")
    var email: String? = null

    @JsonProperty("password")
    var password: String? = null

    constructor() {}

    constructor(firstName: String, secondName: String, lastName: String, email: String, password: String, recaptchaToken: String) {
        this.firstName = firstName
        this.secondName = secondName
        this.lastName = lastName
        this.email = email
        this.password = password
    }

    companion object {
        private const val serialVersionUID = -1764970284520387975L
    }
}