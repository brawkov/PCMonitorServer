package com.pcmonitor.pcmonitorserver.model

import javax.persistence.*

@Entity
@Table(name = "auth_user")
data class UserModel(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = 0,

        @Column(name="username")
        var username: String?=null,

        @Column(name = "email")
        var email: String? = null,

        @Column(name = "password")
        var password: String? = null,

        @Column(name = "enabled")
        var enabled: Boolean = false
)