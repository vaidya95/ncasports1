package com.cricket.ncasports.service;

import com.cricket.ncasports.dto.ScoreDTO;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ScoreUpdateService {

    private final SimpMessagingTemplate messagingTemplate;

    // Inject SimpMessagingTemplate to send messages over WebSocket
    public ScoreUpdateService(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    // Send updated score to the WebSocket topic "/topic/score"
    public void sendScoreUpdate(ScoreDTO scoreDTO) {
        messagingTemplate.convertAndSend("/topic/score", scoreDTO);
    }
}
