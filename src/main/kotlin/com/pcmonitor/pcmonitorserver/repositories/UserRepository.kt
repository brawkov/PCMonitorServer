package com.pcmonitor.pcmonitorserver.repositories

import com.pcmonitor.pcmonitorserver.models.UserModel
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<UserModel, Long> {
    fun existsByUsername(@Param("username") username: String): Boolean

    fun existsByEmail(@Param("email") email: String): Boolean

    fun findByEmail(@Param("email") email: String): UserModel

}