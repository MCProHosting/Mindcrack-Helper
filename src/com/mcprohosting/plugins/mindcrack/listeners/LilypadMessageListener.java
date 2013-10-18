package com.mcprohosting.plugins.mindcrack.listeners;

import java.io.UnsupportedEncodingException;

import com.mcprohosting.plugins.mindcrack.teleporters.TeleportSigns;

import lilypad.client.connect.api.event.EventListener;
import lilypad.client.connect.api.event.MessageEvent;

public class LilypadMessageListener {

	@EventListener
	public void onMessage(MessageEvent event) {
		if (event.getChannel().equals("mindcrack")) {
			String sender = event.getSender();
			String message = null;
			try {
				message = event.getMessageAsString();
			} catch (UnsupportedEncodingException e) {
				
			}
			
			if (message != null) {
				TeleportSigns.updateStatus(sender, message);
			}
		}
	}
}
