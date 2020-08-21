package com.pcmonitor.pcmonitorserver.models

import org.springframework.data.rest.core.config.Projection
import javax.persistence.*

@Entity
@Table(name = "pc")
data class PCModel(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "pc_id")
        val pcId: Long? = 0,

        @Column(name = "pc_group_id")
        var pcGroupId: Long = 1,

        @Column(name = "pc_name")
        var pcName: String,

        @Column(name = "pc_write_history")
        var pcWriteHistory: Boolean = false,

        @Column(name = "pcActive")
        var pcActive: Boolean = false,

        @Column(name = "pc_cpu_cores_inf")
        var pcCpuCoresInf: Short? = 0,

        @Column(name = "pc_ram_inf")
        var pcRamInf: Short? = 0,

        @Column(name = "pc_rom_inf")
        var pcRomInf: Short? = 0,

        @Column(name = "pc_cpu_frequency")
        var pcCpuFrequency: Short? = 0,

        @Column(name = "pc_cpu_load")
        var pcCpuLoad: Short? = 0,

        @Column(name = "pc_ram_state")
        var pcRamState: Short? = 0,

        @Column(name = "pc_rom_state")
        var pc_rom_state: Short? = 0,

        @Column(name = "pc_time_work")
        var pcTimeWork: Int? = 0

)
