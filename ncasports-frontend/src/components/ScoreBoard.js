import React, { useState, useEffect } from 'react';
import { connectToWebSocket, disconnectFromWebSocket } from '../websocket';
import MatchScore from './MatchScore';

const ScoreBoard = () => {
    const [scores, setScores] = useState([]);

    const handleScoreUpdate = (score) => {
        setScores((prevScores) => {
            const updatedScores = [...prevScores];
            const matchIndex = updatedScores.findIndex((s) => s.matchId === score.matchId);
            if (matchIndex !== -1) {
                updatedScores[matchIndex] = score;
            } else {
                updatedScores.push(score);
            }
            return updatedScores;
        });
    };

    useEffect(() => {
        connectToWebSocket(handleScoreUpdate);
        return () => disconnectFromWebSocket();
    }, []);

    return (
        <div>
            <h1>Live Scoreboard</h1>
            <ul>
                {scores.length === 0 ? (
                    <li>No matches in progress</li>
                ) : (
                    scores.map((score) => (
                        <li key={score.matchId}>
                            <MatchScore matchId={score.matchId} score={score} />
                        </li>
                    ))
                )}
            </ul>
        </div>
    );
};

export default ScoreBoard;
