package com.pcmonitor.pcmonitorserver.controllers


import com.pcmonitor.pcmonitorserver.controllers.jwt.JwtProvider
import com.pcmonitor.pcmonitorserver.models.UserModel
import com.pcmonitor.pcmonitorserver.repositories.UserRepository
import com.pcmonitor.pcmonitorserver.services.RegistrationRequestValidation

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.validation.Errors
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.*


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController {

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var encoder: PasswordEncoder

    @Autowired
    lateinit var jwtProvider: JwtProvider


    @Autowired
    private val newUserValidator: RegistrationRequestValidation? = null


    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: UserModel): ResponseEntity<*> {

        if (userRepository.existsByEmail(loginRequest.email)) {

            val user: UserModel = userRepository.findByEmail(loginRequest.email)
            val authentication: Authentication
            //Проверка подлинности пароля
            try {
                authentication = authenticationManager.authenticate(
                        UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password))
            } catch (e: Exception) {
                return ResponseEntity(mapOf("message" to "Неверный Email или пароль"),
                        HttpStatus.UNAUTHORIZED)
            }
            SecurityContextHolder.getContext().authentication = authentication
            //Выдаем jwt
            val jwt: String = jwtProvider.generateJwtToken(user.email)
            return ResponseEntity.ok(mapOf("accessToken" to jwt, "email" to user.email, "username" to user.username))

        } else {
            return ResponseEntity(mapOf("message" to "Неверный Email или пароль"), HttpStatus.UNAUTHORIZED)
        }
    }


    @InitBinder
    fun initBinder(binder: WebDataBinder) {
        binder.addValidators(newUserValidator)
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody @Validated newUser: UserModel, errors: Errors): ResponseEntity<*> {

        val errorMessage: MutableMap<String, String> = mutableMapOf()

        return if (errors.hasErrors()) {
            for (error in errors.fieldErrors) {
                errorMessage += error.code.toString() to error.defaultMessage.toString()
            }
            ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
        } else {

            //Если нет ошибок создем нового пользователя
            val user = UserModel(
                    0,
                    newUser.username,
                    newUser.email,
                    encoder.encode(newUser.password),
                    true
            )

            userRepository.save(user)

            ResponseEntity(mapOf("message" to "Пользователь успешно зарегистрирован!"),
                    HttpStatus.OK)
        }
    }
}