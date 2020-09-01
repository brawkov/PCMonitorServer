package com.pcmonitor.pcmonitorserver


import com.fasterxml.jackson.databind.ObjectMapper
import com.pcmonitor.pcmonitorserver.controllers.ChatMessage
import org.springframework.stereotype.Controller
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler


@Controller
class StatePcWebSocetHandler() : TextWebSocketHandler() {

    private val pcSessions = HashMap<WebSocketSession, Long>()
    private val clientSession = HashMap<WebSocketSession, Long>()


    // Вызывается после установки соединения. Добавляет клиента в общий список.
    override fun afterConnectionEstablished(session: WebSocketSession) {
        val pcId = session.uri!!.query.substringAfter("pcId=").toLong()
        when (session.uri!!.path.substring(1).substringBefore('/')) {
            "sendStatePC" -> {
                println("add Agent(ON PC)")
                pcSessions[session] = pcId
            }
            "getStatePC" -> {
                println("add Client")
                clientSession[session] = pcId
            }
        }
    }

    // Вызывается после прерывания соединения. Удаляет клиента из hasmap.
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        val pcId = session.uri!!.query.substringAfter("pcId=").toLong()
        when (session.uri!!.path.substring(1).substringBefore('/')) {
            "sendStatePC" -> {
                println("delete Agent(OFF PC)")
                pcSessions.minusAssign(session)
            }
            "getStatePC" -> {
                println("delete Client")
                clientSession.minusAssign(session)
            }
        }
    }

    // Вызывается после получения сообщения. Рассылает его всем подключенным клиентам.
    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        if (session.uri!!.path.substring(1).substringBefore('/') =="sendStatePC"){
            println("message.payload: " + message.payload)
            val msgJson = ObjectMapper().readTree(message.payload)

            val recipients = clientSession.filter { (_, value) ->
                    value == pcSessions[session]
                }

//            println("store sessionList: ")
//            clientSession.forEach {
//                println("    key: " + it.key)
//                println("       value: " + it.value)
//            }

                recipients.forEach {
                    it.key.sendMessage(TextMessage(message.payload))
                }
        }

    }

//    private fun sendMessageToClient(message: TextMessage,
//                                    establishedSession: WebSocketSession) {
//        try {
//            establishedSession.sendMessage(TextMessage(message.payload))
//        } catch (e: IOException) {
//           println("Failed to send message. $e")
//        }
//    }
}