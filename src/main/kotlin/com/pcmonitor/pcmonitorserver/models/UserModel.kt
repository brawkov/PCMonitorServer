package com.pcmonitor.pcmonitorserver.models


import javax.persistence.*

@Entity
@Table(name = "auth_user")
data class UserModel(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val id: Long? = 0,

        @Column(name = "username")
        var username: String? = null,

        @Column(name = "email")
        var email: String,

        @Column(name = "password")
        var password: String,

        @Column(name = "enabled")
        var enabled: Boolean = false
)