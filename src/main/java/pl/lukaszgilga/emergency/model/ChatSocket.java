package pl.lukaszgilga.emergency.model;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@EnableWebSocket
@Component
public class ChatSocket extends TextWebSocketHandler implements WebSocketConfigurer {
    private List<User> users = new ArrayList<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(this, "/chat").setAllowedOrigins("*");

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        User user = new User(session);
        users.add(user);

    }

    private void welcomeToAll(WebSocketSession session){

    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        for (User webSocketSession : sessions) {
            TextMessage newMessage = new TextMessage(nicknames.get(0).getMessage().getPayload()+"("
                + formatter.format(LocalDateTime.now())
                + ")" + message.getPayload()
        );
            webSocketSession.sendMessage(newMessage);

        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        TextMessage goodbye = new TextMessage("("
                +formatter.format(LocalDateTime.now())
                +") User left");
        for (WebSocketSession webSocketSession : sessions) {
            webSocketSession.sendMessage(goodbye);
        }
        sessions.remove(session);
    }
}
