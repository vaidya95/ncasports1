import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';

let stompClient = null;

export const connectToWebSocket = (onScoreUpdate) => {
    const socket = new SockJS('http://localhost:8080/ws');
    stompClient = Stomp.over(socket);

    stompClient.connect({}, () => {
        console.log('Connected to WebSocket');
        stompClient.subscribe('/topic/score-updates', (message) => {
            const updatedScore = JSON.parse(message.body);
            console.log('Received updated score:', updatedScore);
            onScoreUpdate(updatedScore); // Use onScoreUpdate callback to update the scoreboard
        });
    }, (error) => {
        console.error('WebSocket connection error:', error);
    });
};

export const disconnectFromWebSocket = () => {
    if (stompClient) {
        stompClient.disconnect(() => {
            console.log('Disconnected from WebSocket');
        });
    }
};
