import React from 'react';

// MatchScore component to show live score, overs, and wickets for each match
const MatchScore = ({ matchId, score }) => (
    <div className="match-score">
        <h3>Match ID: {matchId}</h3>
        {score ? (
            <div>
                <p>Score: {score.teamAScore} - {score.teamBScore}</p>
                <p>{score.teamA} - {score.teamAWickets} wickets, {score.teamAOver} overs</p>
                <p>{score.teamB} - {score.teamBWickets} wickets, {score.teamBOver} overs</p>
                <p>Winner: {score.winner}</p>
            </div>
        ) : (
            <p>No score yet</p>
        )}
    </div>
);

export default MatchScore;
