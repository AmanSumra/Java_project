package com.example.handler;

import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;

import javax.crypto.SecretKey;

public class WebSocketMessageHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Retrieve AES key from session attributes
        SecretKey secretKey = (SecretKey) session.getAttributes().get("AESKey");

        // Decrypt the received message
        String decryptedMessage = AESUtils.decryptWithAES(message.getPayload(), secretKey);
        System.out.println("Received Message: " + decryptedMessage);

        // Encrypt the response message before sending it back
        String encryptedResponse = AESUtils.encryptWithAES("Reply: " + decryptedMessage, secretKey);
        session.sendMessage(new TextMessage(encryptedResponse));
    }
}
