package com.pcmonitor.pcmonitorserver.model

import javax.persistence.*

@Entity
@Table(name = "task")
data class TaskModel(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "task_id")
        val taskId: Long? = 0,

        @Column(name = "task_name")
        var taskName: String,

        @Column(name = "task_user")
        var taskUser: String,

        @Column(name = "task_cpu_load")
        var taskCpuLoad: Short? = 0,

        @Column(name = "task_ram")
        var taskRam: Int = 1,

        @Column(name = "task_time")
        var taskTime: Long = 1,

        @Column(name = "task_pc_id")
        var taskPCId: Int = 1
)