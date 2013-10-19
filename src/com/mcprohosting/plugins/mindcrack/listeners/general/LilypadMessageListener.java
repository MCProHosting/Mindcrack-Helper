package com.mcprohosting.plugins.mindcrack.listeners.general;

import com.mcprohosting.plugins.mindcrack.teleporters.TeleportSigns;
import lilypad.client.connect.api.event.EventListener;
import lilypad.client.connect.api.event.MessageEvent;

import java.io.UnsupportedEncodingException;

public class LilypadMessageListener {
	@EventListener
	public void onMessage(MessageEvent event) {
		if (event.getChannel().equals("mindcrack")) {
			String sender = event.getSender();
			String message = null;
			try {
				message = event.getMessageAsString();
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			
			if (message != null) {
				TeleportSigns.updateStatus(sender, message);
			}
		}
	}
}
