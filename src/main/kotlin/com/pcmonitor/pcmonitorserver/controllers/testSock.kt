package com.pcmonitor.pcmonitorserver.controllers
//
//
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
//import com.pcmonitor.pcmonitorserver.config.WebSocketConfig
//import org.springframework.web.socket.*
//import org.springframework.web.socket.handler.TextWebSocketHandler
//import java.util.concurrent.atomic.AtomicLong
//
//
//
//class User(val id: Long, val name: String)
////class Message(val msgType: String, val data: Any)
//
//class ChatHandler(var store: WebSocketConfig.SessionStore) : TextWebSocketHandler() {
//
//    val sessionList = HashMap<WebSocketSession, User>()
//
//    var uids = AtomicLong(0)
//
//    override fun afterConnectionEstablished(session: WebSocketSession) {
////        println("Established session: " + session.uri)
//    }
//    @Throws(Exception::class)
//    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
//        println("Closed session: " + session )
//        store.secondSock.minusAssign(session)
//
//    }
//
//    public override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
//        println("SockJS: " + message.payload)
//        val json = ObjectMapper().readTree(message?.payload)
//        // {type: "join/say", data: "name/msg"}
//        when (json.get("type").asText()) {
//            "join" -> {
//                store.secondSock.put(session!!, json.get("data").asInt())
//            }
//        }
//    }
//}
//
//
