package com.pcmonitor.pcmonitorserver.controller

//import javax.validation.Valid
import java.util.*
import java.util.stream.Collectors


import org.springframework.security.core.Authentication
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


import com.pcmonitor.pcmonitorserver.controller.LoginController
import com.pcmonitor.pcmonitorserver.controller.RegistrationController
import com.pcmonitor.pcmonitorserver.controller.JwtResponse
import com.pcmonitor.pcmonitorserver.controller.ResponseMessage
import com.pcmonitor.pcmonitorserver.model.UserModel
import com.pcmonitor.pcmonitorserver.repository.UserRepository
import com.pcmonitor.pcmonitorserver.controller.JwtProvider


@CrossOrigin(origins = ["*"], maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
class AuthController() {

    @Autowired
    lateinit var authenticationManager: AuthenticationManager

    @Autowired
    lateinit var userRepository: UserRepository

//    @Autowired
//    lateinit var roleRepository: RoleRepository

    @Autowired
    lateinit var encoder: PasswordEncoder

    @Autowired
    lateinit var jwtProvider: JwtProvider


    @PostMapping("/signin")
    fun authenticateUser(@RequestBody loginRequest: LoginController): ResponseEntity<*> {


        val userCandidate: Optional <UserModel> = userRepository.findByEmail(loginRequest.email!!)

        if (userCandidate.isPresent) {
            val user: UserModel = userCandidate.get()
            val authentication = authenticationManager.authenticate(
                    UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password))
            SecurityContextHolder.getContext().setAuthentication(authentication)
            val jwt: String = jwtProvider.generateJwtToken(user.email!!)
            return ResponseEntity.ok(JwtResponse(jwt, user.email))
//            val tmp =UsernamePasswordAuthenticationToken(loginRequest.email, loginRequest.password)
//            return ResponseEntity.ok("$tmp")
        } else {
            return ResponseEntity(ResponseMessage("User not found!"),
                    HttpStatus.BAD_REQUEST)
        }
    }

    @PostMapping("/signup")
    fun registerUser( @RequestBody newUser: RegistrationController): ResponseEntity<*> {

        val userCandidate: Optional <UserModel> = userRepository.findByEmail(newUser.email!!)

        if (!userCandidate.isPresent) {
            if (usernameExists(newUser.email!!)) {
                return ResponseEntity(ResponseMessage("Username is already taken!"),
                        HttpStatus.BAD_REQUEST)
            } else if (emailExists(newUser.email!!)) {
                return ResponseEntity(ResponseMessage("Email is already in use!"),
                        HttpStatus.BAD_REQUEST)
            }

            // Creating user's account
            val user = UserModel(
                    0,
                    newUser.firstName!!,
                    newUser.secondName!!,
                    newUser.lastName!!,
                    newUser.email!!,
                    encoder.encode(newUser.password),
                    true
            )

            userRepository.save(user)

            return ResponseEntity(ResponseMessage("User registered successfully!"), HttpStatus.OK)
        } else {
            return ResponseEntity(ResponseMessage("User already exists!"),
                    HttpStatus.BAD_REQUEST)
        }
    }

    private fun emailExists(email: String): Boolean {
        return userRepository.findByEmail(email).isPresent
    }

    private fun usernameExists(username: String): Boolean {
        return userRepository.findByEmail(username).isPresent
    }

}