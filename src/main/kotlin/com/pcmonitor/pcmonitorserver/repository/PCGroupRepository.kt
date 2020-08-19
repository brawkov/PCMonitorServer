package com.pcmonitor.pcmonitorserver.repository

import java.util.Optional
import com.pcmonitor.pcmonitorserver.model.PCGroupModel
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PCGroupRepository: JpaRepository<PCGroupModel, Long> {
    fun findByGroupId(@Param("groupId") groupId: Long): Optional<PCGroupModel>
}