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

        @Column(name = "pc_cpu_phys_cores_inf")
        var pcCpuPhysCoresInf: Short? = 0,

        @Column(name = "pc_cpu_logic_cores_inf")
        var pcCpuLogicCoresInf: Short? = 0,

        @Column(name = "pc_ram_inf")
        var pcRamInf: Float? = 0F,

        @Column(name = "pc_rom_inf")
        var pcRomInf: Float? = 0F,

        @Column(name = "pc_cpu_frequency")
        var pcCpuFrequency: Float? = 0F,

        @Column(name = "pc_cpu_load")
        var pcCpuLoad: Float? = 0F,

        @Column(name = "pc_ram_state")
        var pcRamState: Float? = 0F,

        @Column(name = "pc_rom_state")
        var pcRomState: Float? = 0F,

        @Column(name = "pc_time_work")
        var pcTimeWork: Long? = 0

)
