package com.pcmonitor.pcmonitorserver.repository

import java.util.Optional
import com.pcmonitor.pcmonitorserver.model.UserModel
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import javax.transaction.Transactional

@Repository
interface UserRepository: JpaRepository<UserModel, Long> {
    fun existsByUsername(@Param("username") username: String): Boolean

    fun findByUsername(@Param("username") username: String): Optional<UserModel>

    fun existsByEmail(@Param("email") email: String): Boolean

    fun findByEmail(@Param("email") email: String): Optional<UserModel>

    @Transactional
    fun deleteByEmail(@Param("email") email: String)

}