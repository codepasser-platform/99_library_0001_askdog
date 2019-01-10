package com.askdog.web.configuration.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

@Component
public class WebSocketHandler extends AbstractWebSocketHandler {

    @Autowired private WebSocketManager webSocketManager;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);

        if (session.getPrincipal() == null) {
            session.close(new CloseStatus(CloseStatus.POLICY_VIOLATION.getCode(), "Please Login!"));
            return;
        }

        webSocketManager.addSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        webSocketManager.removeSession(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        User user = (User) ((UsernamePasswordAuthenticationToken) session.getPrincipal()).getPrincipal();
//        Map<String, Object> data = Maps.newHashMap();
//        data.put("username", user.getName());
//        data.put("message", message.getPayload());
//        webSocketManager.broadcast(data);
    }
}
