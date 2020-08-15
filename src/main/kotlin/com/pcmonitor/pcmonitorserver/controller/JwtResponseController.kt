package com.pcmonitor.pcmonitorserver.controller

import org.springframework.security.core.GrantedAuthority

class JwtResponse(var accessToken: String?, var email: String?) {
    var type = "Bearer"
}


