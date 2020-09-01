package com.pcmonitor.pcmonitorserver.controllers


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.messaging.handler.annotation.MessageExceptionHandler
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Controller


//@Controller
//class ChatController {
//    @Autowired
//    private lateinit var messagingTemplate: SimpMessageSendingOperations
//
//    @MessageMapping("/chat.sendMessage")
////    @SendTo("/topic/public")
//    fun sendMessage(chatMessage: ChatMessage): ChatMessage {
//        println("sendMessage chatMessage: ${chatMessage}")
//        chatMessage.content = "update massege"
//        messagingTemplate.convertAndSend("/topic/public", chatMessage)
//        return chatMessage
//    }
//
//
//}