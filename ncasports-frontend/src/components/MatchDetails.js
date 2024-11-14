import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

const MatchDetails = () => {
  const { matchId } = useParams(); // Get matchId from the URL params
  const [matchDetails, setMatchDetails] = useState(null); // To store match details
  const [score, setScore] = useState(null); // To store score details

  // Fetch match and score data when the component mounts
  useEffect(() => {
    // Fetch match details by matchId
    axios
      .get(`http://localhost:8080/api/matches/${matchId}`)
      .then((response) => setMatchDetails(response.data))
      .catch((error) => console.error('Error fetching match details:', error));

    // Fetch score details by matchId
    axios
      .get(`http://localhost:8080/api/scores/${matchId}`)
      .then((response) => setScore(response.data))
      .catch((error) => console.error('Error fetching score:', error));
  }, [matchId]);

  // Show loading message while data is being fetched
  if (!matchDetails || !score) {
    return <p>Loading match details...</p>;
  }

  return (
    <div>
      <h2>Match Details</h2>
      {/* Display Team Names and Scheduled Date */}
      <p><strong>{matchDetails.teamA}</strong> vs <strong>{matchDetails.teamB}</strong></p>
      <p><strong>Scheduled on:</strong> {new Date(matchDetails.scheduledDate).toLocaleString()}</p>
      <p><strong>Toss Winner:</strong> {matchDetails.tossWinner}</p>
      <p><strong>Toss Decision:</strong> {matchDetails.tossDecision}</p>

      {/* Display Current Score */}
      <h3>Current Score</h3>
      <p>{matchDetails.teamA}: {score.teamAScore} ({score.teamAWickets} wickets, {score.teamAOver} overs)</p>
      <p>{matchDetails.teamB}: {score.teamBScore} ({score.teamBWickets} wickets, {score.teamBOver} overs)</p>

      {/* Display Winner */}
      <p><strong>Winner:</strong> {score.winner || 'Tie'}</p>
    </div>
  );
};

export default MatchDetails;
