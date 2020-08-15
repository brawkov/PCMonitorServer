package com.pcmonitor.pcmonitorserver.model

import javax.persistence.*

@Entity
@Table(name = "auth_user")
data class UserModel(

        @Id
//        @GeneratedValue(strategy = GenerationType.AUTO)
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = 0,

        @Column(name = "first_name")
        var firstName: String? = null,

        @Column(name = "second_name")
        var secondName: String? = null,

        @Column(name = "last_name")
        var lastName: String? = null,

        @Column(name = "email")
        var email: String? = null,

        @Column(name = "password")
        var password: String? = null,

        @Column(name = "enabled")
        var enabled: Boolean = false
)