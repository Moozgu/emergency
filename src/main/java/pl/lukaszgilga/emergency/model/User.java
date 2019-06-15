package pl.lukaszgilga.emergency.model;

import org.springframework.web.socket.WebSocketSession;

import javax.websocket.Session;

public class User {
    private String nickname;
    private WebSocketSession session;

    public User(WebSocketSession session) {
        this.session = session;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public WebSocketSession getSession() {
        return session;
    }
}
