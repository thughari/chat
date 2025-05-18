package com.thughari.chat.controller;

import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.thughari.chat.model.ChatMessage;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ChatController {
	
	public static AtomicInteger onlineUsers = new AtomicInteger(0);

	@MessageMapping("/chat.sendMessage")
	@SendTo("/topic/public")
	public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
		log.info("User '{}' sent message: {}", chatMessage.getSender(), chatMessage.getContent());
		chatMessage.setUsersOnline(onlineUsers.get());
		return chatMessage;
	}
	
	@MessageMapping("/chat.addUser")
	@SendTo("/topic/public")
	public ChatMessage addUser(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
		headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());  //add username in websocket session
		log.info("User '{}' joined the chat", chatMessage.getSender());
		onlineUsers.incrementAndGet();
		log.info("added: " + onlineUsers.get());
//		broadcastOnlineUsers();
		chatMessage.setUsersOnline(onlineUsers.get());
		return chatMessage;
	}
	
//	@MessageMapping("/chat.removeUser")
//    @SendTo("/topic/public")
//    public ChatMessage removeUser(@Payload ChatMessage chatMessage) {
//        chatMessage.setContent(chatMessage.getSender() + " left!");
//        onlineUsers.decrementAndGet(); // Decrement online users count
//        broadcastOnlineUsers();
//        log.info("users online {}", onlineUsers.get());
//        chatMessage.setUsersOnline(onlineUsers.get());
//        return chatMessage;
//    }
//	
//	@SendTo("/topic/onlineUsers")
//    public int broadcastOnlineUsers() {
//        return onlineUsers.get();
//    }

}
