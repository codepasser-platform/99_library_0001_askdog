package com.askdog.web.configuration.websocket;

public class WebSocketException extends Exception {

    public WebSocketException(String message) {
        super(message);
    }

    public WebSocketException(String message, Throwable cause) {
        super(message, cause);
    }
}
