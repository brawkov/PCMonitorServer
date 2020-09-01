package com.pcmonitor.pcmonitorserver.config


import com.pcmonitor.pcmonitorserver.StatePcWebSocetHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.*
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean



@Configuration
@EnableWebSocket
class WebSocketConfig : WebSocketConfigurer {

    val handler = StatePcWebSocetHandler()
    @Bean
    fun createWebSocketContainer(): ServletServerContainerFactoryBean? {
        val container = ServletServerContainerFactoryBean()
        container.setMaxTextMessageBufferSize(64 * 1024)
        container.setMaxBinaryMessageBufferSize(64 * 1024)
        return container
    }

    @Override
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(handler, "/sendStatePC").setAllowedOrigins("*")
        registry.addHandler(handler, "/getStatePC").setAllowedOrigins("*").withSockJS()
    }
}


//@Configuration
//@Order(Ordered.HIGHEST_PRECEDENCE + 99)
//@EnableWebSocketMessageBroker
//class WebSocketMessageBrokerConfig : WebSocketMessageBrokerConfigurer {
// авторизация по jwt (не работает)
//    @Autowired
//    private val tokenProvider: JwtProvider? = null
//
//    @Autowired
//    private val userDetailsService: UserDetailsServiceImpl? = null
//
//    @Autowired
//    lateinit var authenticationManager: AuthenticationManager
// авторизация по jwt (не работает) v1
//    @Override
//    override fun configureClientInboundChannel(registration: ChannelRegistration) {
//        registration.interceptors(object : ChannelInterceptor {
//            @Override
//            override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
//                println("message: $message")
//                val accessor: StompHeaderAccessor = StompHeaderAccessor.wrap(message!!)
//                val tokenList: List<String>? = accessor.getNativeHeader("Authorization")
//                var token: String? = null
//                if (tokenList != null || tokenList!!.size > 0) {
//                    token = tokenList!![0];
//                }
//                println("token: $token")
//
//                token = token!!.replace("Bearer ", "")
//                val isValid = tokenProvider!!.validateJwtToken(token)
//                println("isValid: $isValid")
//
//                val email = tokenProvider!!.getUserEmailFromJwtToken(token!!)
//                val userDetails = userDetailsService!!.loadUserByUsername(email)
//                val authentication = UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.authorities)
//                println("authentication: $authentication")
////                    authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
//                SecurityContextHolder.getContext().authentication = authentication
//
//
////                val yourAuth: Principal = authentication
//
//                val yourAuth: Principal = authenticationManager.authenticate(
//                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities))
////
//                accessor.user = yourAuth;
//                accessor.setLeaveMutable(true);
//                return MessageBuilder.createMessage(message.payload, accessor.messageHeaders)
//            }
//        })
//
//    }


//  тоже авторизация по jwt (не работает)
//    private val userRegistry: DefaultSimpUserRegistry = DefaultSimpUserRegistry()
//    private val resolver: DefaultUserDestinationResolver = DefaultUserDestinationResolver(userRegistry)
//
//    @Bean
//    @Primary
//    public fun myUserRegistry(): SimpUserRegistry {
//        return userRegistry
//    }
//
//    @Bean
//    @Primary
//    public fun myUserDestinationResolver(): UserDestinationResolver {
//        return resolver
//    }

//    @Override
//    override fun configureClientInboundChannel(registration: ChannelRegistration) {
//        registration.interceptors(object : ChannelInterceptor {
//            override fun preSend(message: Message<*>, channel: MessageChannel): Message<*>? {
//                val accessor: StompHeaderAccessor = StompHeaderAccessor.wrap(message)
//
//                val tokenList: MutableList<String>? = accessor.getNativeHeader("Authorization")
//                accessor.removeNativeHeader("Authorization")
//
//                var token: String? = null;
//                if (tokenList != null && tokenList.size > 0) {
//                    token = tokenList[0];
//                }
//                else{
//                    return null
//                }
//
//                token = token!!.replace("Bearer ", "")
//                if( tokenProvider!!.validateJwtToken(token)) {
//                    val email = tokenProvider!!.getUserEmailFromJwtToken(token!!)
//                    val userDetails = userDetailsService!!.loadUserByUsername(email)
//                    val authentication = UsernamePasswordAuthenticationToken(
//                            userDetails, null, userDetails.authorities)
////                  authentication.details = WebAuthenticationDetailsSource().buildDetails(request)
////                    SecurityContextHolder.getContext().authentication = authentication
////                println("auten: "+  SecurityContextHolder.getContext().authentication.isAuthenticated)
//
////                val yourAuth: Principal = authentication
////                val yourAuth: Principal = authenticationManager.authenticate(
////                        UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities))
//////
////                accessor.user = yourAuth;
//                    return message
//                }
//                else return null

                // not documented anywhere but necessary otherwise NPE in StompSubProtocolHandler!
//                accessor.setLeaveMutable(true);
//                return MessageBuilder.createMessage(message.payload, accessor.messageHeaders);
//            }
//        })
//    }
//}

//@Configuration
//class WebSocketSecurityConfig : AbstractSecurityWebSocketMessageBrokerConfigurer() {
//
//
//    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
//        registry.setApplicationDestinationPrefixes("/app")
//        registry.enableSimpleBroker("/topic")
//    }
//
//    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
//        registry.addEndpoint("/chat").setAllowedOrigins("*").withSockJS()    //setHandshakeHandler(MyHandshakeHandler())
//    }
//
//
////    override fun sameOriginDisabled(): Boolean {
////        return true
////    }
//
//    override fun configureWebSocketTransport(registration: WebSocketTransportRegistration) {
//        registration.setMessageSizeLimit(64 * 1024)
//    }
////конфиг требующий авторизации при взаимодействие с сокетом
////    override fun configureInbound(messages: MessageSecurityMetadataSourceRegistry) {
////        messages.simpTypeMatchers(
//////                SimpMessageType.CONNECT,
//////                SimpMessageType.SUBSCRIBE,
//////                SimpMessageType.MESSAGE,
//////                SimpMessageType.HEARTBEAT,
////                SimpMessageType.UNSUBSCRIBE,
////                SimpMessageType.DISCONNECT).permitAll().anyMessage().permitAll()
////                .simpDestMatchers("/topic/**").authenticated().anyMessage().authenticated()
////    }
//
//
//}
////
////@Configuration
////@EnableWebSocketMessageBroker
////class WebSocketMessageBrokerConfig : WebSocketMessageBrokerConfigurer {
////    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
////        registry.addEndpoint("/ws").setAllowedOrigins("*") .withSockJS()
////    }
////
////    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
////        registry.setApplicationDestinationPrefixes("/app")
////        registry.enableSimpleBroker("/topic")
////    }
////}