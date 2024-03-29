package com.pcmonitor.pcmonitorserver.repositories

import java.util.Optional
import com.pcmonitor.pcmonitorserver.models.PCGroupModel
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
interface PCGroupRepository : JpaRepository<PCGroupModel, Long> {
    fun findByGroupName(@Param("groupName") groupName: String): Optional<PCGroupModel>
    fun existsByGroupName(@Param("groupName") groupName: String): Boolean
    fun existsByGroupId(@Param("groupId") groupId: Long): Boolean

}