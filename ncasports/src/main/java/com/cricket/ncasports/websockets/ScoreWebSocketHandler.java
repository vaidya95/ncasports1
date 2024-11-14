package com.cricket.ncasports.websockets;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.stereotype.Component;

@Component
public class ScoreWebSocketHandler extends TextWebSocketHandler {

    // Handle incoming WebSocket message
    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // Handle the incoming message (for example, a score update)
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);

        // Broadcast a message (here you could implement broadcasting score updates to the front end)
        TextMessage broadcastMessage = new TextMessage("New score update: " + payload);
        try {
            session.sendMessage(broadcastMessage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Optionally, handle after connection open
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println("WebSocket connection established with session: " + session.getId());
    }
}
