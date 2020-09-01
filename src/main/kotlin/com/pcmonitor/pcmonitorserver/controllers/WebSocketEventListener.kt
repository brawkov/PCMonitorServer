package com.pcmonitor.pcmonitorserver.controllers
//
//
//import antlr.debug.MessageEvent
//import com.pcmonitor.pcmonitorserver.config.WebSocketConfig
//import com.pcmonitor.pcmonitorserver.controllers.jwt.JwtProvider
//import com.pcmonitor.pcmonitorserver.services.UserDetailsServiceImpl
//import org.slf4j.Logger
//import org.slf4j.LoggerFactory
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.context.event.EventListener
//import org.springframework.messaging.simp.SimpMessageSendingOperations
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
//import org.springframework.security.core.context.SecurityContextHolder
//import org.springframework.stereotype.Component
//import org.springframework.web.socket.messaging.*
//
//
//@Component
//class WebSocketEventListener {
//
//    @Autowired
//    private lateinit var messagingTemplate: SimpMessageSendingOperations
//
//
//    @EventListener
//    fun handleWebSocketConnectListener(event: SessionConnectedEvent) {
//        println("handleWebSocketConnectListener event: ${event.message}")
//        val accessor: StompHeaderAccessor = StompHeaderAccessor.wrap(event.message)
//        accessor.sessionId
//
//        logger.info("Received a new web socket connection")
//    }
//
//    @EventListener
//    fun handleWebSocketSubscribeListener(event: SessionSubscribeEvent) {
//        println("handleWebSocketSubscribeListener event: ${event.user}")
//        logger.info("handleWebSocketSubscribeListener")
//    }
//
//
//    @EventListener
//    fun handleWebSocketDisconnectListener(event: SessionDisconnectEvent?) {
//        val headerAccessor = StompHeaderAccessor.wrap(event!!.message)
//        val username = headerAccessor.sessionAttributes!!["username"] as String?
//        println("handleWebSocketDisconnectListener event: $event")
//        if (username != null) {
//            logger.info("User Disconnected : $username")
//            val chatMessage = ChatMessage()
//            chatMessage.type = ChatMessage.MessageType.CHAT
//            chatMessage.content = "fdsfsdfsdfs"
//            messagingTemplate.convertAndSend("/topic/public", chatMessage)
//        }
//    }
//
//    companion object {
//        private val logger: Logger = LoggerFactory.getLogger(WebSocketEventListener::class.java)
//    }
//}