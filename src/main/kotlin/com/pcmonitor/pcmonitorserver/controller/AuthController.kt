package com.pcmonitor.pcmonitorserver.controller


import com.pcmonitor.pcmonitorserver.ResponseMessage
import com.pcmonitor.pcmonitorserver.controller.jwt.JwtProvider
import com.pcmonitor.pcmonitorserver.controller.jwt.JwtResponse
import com.pcmonitor.pcmonitorserver.model.UserModel
import com.pcmonitor.pcmonitorserver.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.util.MultiValueMap
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController() {

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var encoder: PasswordEncoder

    @Autowired
    lateinit var jwtProvider: JwtProvider


    @PostMapping("/signin")
    fun authenticateUser(@RequestBody @Validated loginRequest: UserModel): ResponseEntity<*> {
        val userCandidate: Optional<UserModel> = userRepository.findByEmail(loginRequest.email!!)
        if (userCandidate.isPresent) {
            val user: UserModel = userCandidate.get()
            val authentication: Authentication
            //Проверка подлинности пароля
            try {
                authentication = authenticationManager.authenticate(
                        UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password))
            } catch (e: Exception) {
                return ResponseEntity(mapOf("message" to "Неверный Email или пароль"),
                        HttpStatus.UNAUTHORIZED)
            }
            SecurityContextHolder.getContext().setAuthentication(authentication)
            val jwt: String = jwtProvider.generateJwtToken(user.email!!)
            return ResponseEntity.ok(JwtResponse(jwt, user.email, user.username))

        } else {
            return ResponseEntity(mapOf("message" to "Неверный Email или пароль"), HttpStatus.UNAUTHORIZED)
        }
    }

    @PostMapping("/signup")
    fun registerUser(@RequestBody newUser: UserModel): ResponseEntity<*> {

        val errorMessage: MutableMap<String, String> = mutableMapOf()
        val userCandidate: Optional<UserModel> = userRepository.findByEmail(newUser.email!!)

        if (!emailExists(newUser.email!!)) {
            errorMessage += "messageEmailExists" to "Данный Email уже зарегестрироан!"
        }
        if (!usernameExists(newUser.email!!)) {
            errorMessage += "messageUsernameExists" to "Имя пользователя уже занято!"
        }
        return ResponseEntity(errorMessage,
                        HttpStatus.BAD_REQUEST)

//        if (!userCandidate.isPresent) {
//            if (usernameExists(newUser.username!!)) {
//                return ResponseEntity(mapOf("message1" to "Имя пользователя уже занято!", "message1" to "Данный Email уже зарегестрироан!"),
//                        HttpStatus.BAD_REQUEST)
//            } else if (emailExists(newUser.email!!)) {
//                return ResponseEntity(ResponseMessage("Данный Email уже зарегестрироан!"),
//                        HttpStatus.BAD_REQUEST)
//            }
//
//            // Creating user's account
//            val user = UserModel(
//                    0,
//                    newUser.username!!,
//                    newUser.email!!,
//                    encoder.encode(newUser.password),
//                    true
//            )
//
//            userRepository.save(user)
//
//            return ResponseEntity(ResponseMessage("User registered successfully!"), HttpStatus.OK)
//        } else {
//
//
////            return ResponseEntity(ResponseMessage("User already exists!"),
////                    HttpStatus.BAD_REQUEST)
//            return ResponseEntity(mapOf("message." to "Имя пользователя уже занято!", "message2" to "Данный Email уже зарегестрироан!"),
//                    HttpStatus.BAD_REQUEST)
//        }
    }

    private fun emailExists(email: String): Boolean {
        return userRepository.findByEmail(email).isPresent
    }

    private fun usernameExists(username: String): Boolean {
        return userRepository.findByUsername(username).isPresent
    }

    companion object {
        private val logger = LoggerFactory.getLogger(AuthController::class.java)
    }
}