package com.pcmonitor.pcmonitorserver.models

import javax.persistence.*

@Entity
@Table(name = "pc_group")
data class PCGroupModel(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name="group_id")
        val groupId: Long,

        @Column(name="group_name")
        var groupName: String

)