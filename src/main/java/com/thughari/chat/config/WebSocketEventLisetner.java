package com.thughari.chat.config;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import com.thughari.chat.model.ChatMessage;
import com.thughari.chat.model.MessageType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class WebSocketEventLisetner {
	
	private final SimpMessageSendingOperations messageTemplate;
	
	@EventListener
	public void handleWebSocketDisconnect(SessionDisconnectEvent event) {
		 StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
		 String username = (String) headerAccessor.getSessionAttributes().get("username");
		 
		 if(username!=null) {
			 log.info("user disconnected: "+username);
			 
			 ChatMessage chatMessage = ChatMessage.builder()
					 .type(MessageType.LEAVE)
					 .sender(username)
					 .build();
			 messageTemplate.convertAndSend("/topic/public",chatMessage);
		 }
	}
}
