package com.pcmonitor.pcmonitorserver.repository

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.Optional
import com.pcmonitor.pcmonitorserver.model.PCModel

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.query.Param
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PCRepository : JpaRepository<PCModel, Long> {
    fun findByPcId(@Param("pcId") pcId: Long): PCModel

    fun findByPcGroupId(@Param("pcGroupId") pcGroupId: Long): List<PcIdAndNameProjection>

    interface PcIdAndNameProjection {
        fun getPcId(): Long
        fun getPcName(): String
    }
}