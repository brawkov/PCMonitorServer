package com.pcmonitor.pcmonitorserver.controller.jwt

import org.springframework.security.core.GrantedAuthority

class JwtResponse(var accessToken: String?, var email: String?, var username: String? ) {
    var type = "Bearer"
}


