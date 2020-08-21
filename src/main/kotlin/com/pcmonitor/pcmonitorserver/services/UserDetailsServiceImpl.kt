package com.pcmonitor.pcmonitorserver.services

import com.pcmonitor.pcmonitorserver.models.UserModel
import com.pcmonitor.pcmonitorserver.repositories.UserRepository

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl: UserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepository

    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): UserDetails {
        val user: UserModel = userRepository.findByEmail(email)
                ?: throw UsernameNotFoundException("Пользователь '$email' не найден")

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