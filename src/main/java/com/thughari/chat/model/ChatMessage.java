package com.thughari.chat.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChatMessage {

	private String content;
	private String sender;
	private MessageType type;
	private int usersOnline;
	
}
