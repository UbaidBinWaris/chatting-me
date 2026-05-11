package com.example.chat.websocket;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocket Handler for Real-time Chat
 * Manages WebSocket connections and broadcasts messages to all connected clients
 */
@Component
public class ChatWebSocketHandler extends TextWebSocketHandler {
    
    private static final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();
    
    /**
     * Called when a new WebSocket connection is established
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        sessions.add(session);
        System.out.println("Client connected: " + session.getId());
    }
    
    /**
     * Called when a message is received from the client
     */
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Message received: " + payload);
        
        // Broadcast to all connected clients
        broadcastMessage(payload, session);
    }
    
    /**
     * Called when a WebSocket connection is closed
     */
    @Override
    public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("Client disconnected: " + session.getId());
    }
    
    /**
     * Broadcast message to all connected clients
     */
    private void broadcastMessage(String message, WebSocketSession senderSession) {
        for (WebSocketSession session : sessions) {
            try {
                if (session.isOpen()) {
                    session.sendMessage(new TextMessage(message));
                }
            } catch (IOException e) {
                System.err.println("Error sending message: " + e.getMessage());
            }
        }
    }
    
    /**
     * Get total number of connected clients
     */
    public static int getConnectedClientCount() {
        return sessions.size();
    }
}
