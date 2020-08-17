package com.pcmonitor.pcmonitorserver.services

import com.pcmonitor.pcmonitorserver.repository.UserRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.stream.Collectors
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@Service
class UserDetailsServiceImpl: UserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email).get()
                ?: throw UsernameNotFoundException("User '$email' not found")

        return org.springframework.security.core.userdetails.User
                .withUsername(email)
                .password(user.password)
                .authorities("ROLE_USER")
                .accountExpired(false)
                .accountLocked(false)
                .credentialsExpired(false)
                .disabled(false)
                .build()
    }
}