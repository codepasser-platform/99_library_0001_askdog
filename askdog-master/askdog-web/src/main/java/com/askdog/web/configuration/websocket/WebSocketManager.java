package com.askdog.web.configuration.websocket;

import com.askdog.model.entity.User;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.askdog.common.utils.Json.writeValueAsString;
import static com.google.common.base.Preconditions.checkState;

@Component
public class WebSocketManager {

    private Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    public WebSocketManager addSession(WebSocketSession session) {
        sessions.add(session);
        return this;
    }

    public WebSocketManager removeSession(WebSocketSession session) {
        sessions.remove(session);
        return this;
    }

    public void sendData(String userId, Object data) throws WebSocketException {
        for (WebSocketSession session : sessions) {
            User user = getUser(session);
            if (user.getUuid().equals(userId)) {
                String payload = writeValueAsString(data);
                checkState(payload != null, "the send data can not convert to json string");
                try {
                    session.sendMessage(new TextMessage(payload));
                } catch (IOException e) {
                    throw new WebSocketException("Send data error.", e);
                }
            }
        }
    }

    public void broadcast(Object data) throws WebSocketException {

        for (WebSocketSession session : sessions) {
            String payload = writeValueAsString(data);
            checkState(payload != null, "the send data can not convert to json string");
            try {
                session.sendMessage(new TextMessage(payload));
            } catch (IOException e) {
                throw new WebSocketException("Broadcast error.", e);
            }
        }
    }

    private User getUser(WebSocketSession session) {
        return (User) ((UsernamePasswordAuthenticationToken) session.getPrincipal()).getPrincipal();
    }

}
