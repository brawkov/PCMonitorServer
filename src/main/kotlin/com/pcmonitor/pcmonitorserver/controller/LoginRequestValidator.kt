package com.pcmonitor.pcmonitorserver.controller

import org.springframework.validation.Errors
import org.springframework.validation.Validator

class LoginRequestValidator: Validator {
    override fun validate(target: Any, errors: Errors) {
        TODO("Not yet implemented")
    }

    override fun supports(clazz: Class<*>): Boolean {
        TODO("Not yet implemented")
    }
}