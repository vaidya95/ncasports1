import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';

const UpdateScore = () => {
  const { matchId } = useParams();
  const [score, setScore] = useState({
    teamAScore: 0,
    teamBScore: 0,
    winner: 'Tie',
    teamAWickets: 0,
    teamBWickets: 0,
    teamAOver: 0.0,
    teamBOver: 0.0,
  });
  const [matchDetails, setMatchDetails] = useState(null);
  const [error, setError] = useState(null);

  useEffect(() => {
    axios
      .get(`http://localhost:8080/api/matches/${matchId}`)
      .then((response) => {
        setMatchDetails(response.data);
      })
      .catch((error) => {
        console.error('Error fetching match details:', error);
        setError('Error fetching match details');
      });

    axios
      .get(`http://localhost:8080/api/scores/${matchId}`)
      .then((response) => {
        setScore({
          ...response.data,
          winner: response.data.winner || 'Tie',
        });
      })
      .catch((error) => {
        console.error('Error fetching score:', error);
        setError('Error fetching score');
      });
  }, [matchId]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.put(`http://localhost:8080/api/scores/${matchId}`, score);
      alert('Score updated successfully');
    } catch (error) {
      console.error('Error updating score:', error);
      setError('Error updating score');
    }
  };

  if (error) {
    return <div style={{ color: 'red' }}>{error}</div>;
  }

  if (!matchDetails) {
    return <p>Loading match details...</p>;
  }

  return (
    <div>
      <h2>Update Score for {matchDetails.teamA} vs {matchDetails.teamB}</h2>
      <form onSubmit={handleSubmit}>
        <div>
          <label>{matchDetails.teamA} Score: </label>
          <input
            type="number"
            value={score.teamAScore}
            onChange={(e) => setScore({ ...score, teamAScore: parseInt(e.target.value) })}
          />
        </div>
        <div>
          <label>{matchDetails.teamB} Score: </label>
          <input
            type="number"
            value={score.teamBScore}
            onChange={(e) => setScore({ ...score, teamBScore: parseInt(e.target.value) })}
          />
        </div>
        <div>
          <label>{matchDetails.teamA} Wickets: </label>
          <input
            type="number"
            value={score.teamAWickets}
            onChange={(e) => setScore({ ...score, teamAWickets: parseInt(e.target.value) })}
          />
        </div>
        <div>
          <label>{matchDetails.teamB} Wickets: </label>
          <input
            type="number"
            value={score.teamBWickets}
            onChange={(e) => setScore({ ...score, teamBWickets: parseInt(e.target.value) })}
          />
        </div>
        <div>
          <label>{matchDetails.teamA} Overs: </label>
          <input
            type="number"
            step="0.1"
            value={score.teamAOver}
            onChange={(e) => setScore({ ...score, teamAOver: parseFloat(e.target.value) })}
          />
        </div>
        <div>
          <label>{matchDetails.teamB} Overs: </label>
          <input
            type="number"
            step="0.1"
            value={score.teamBOver}
            onChange={(e) => setScore({ ...score, teamBOver: parseFloat(e.target.value) })}
          />
        </div>
        <div>
          <label>Winner: </label>
          <select
            value={score.winner}
            onChange={(e) => setScore({ ...score, winner: e.target.value })}
          >
            <option value="Tie">Tie</option>
            <option value={matchDetails.teamA}>{matchDetails.teamA}</option>
            <option value={matchDetails.teamB}>{matchDetails.teamB}</option>
          </select>
        </div>
        <button type="submit">Update Score</button>
      </form>

      <h3>Current Score</h3>
      <p>{matchDetails.teamA}: {score.teamAScore} ({score.teamAWickets} wickets, {score.teamAOver} overs)</p>
      <p>{matchDetails.teamB}: {score.teamBScore} ({score.teamBWickets} wickets, {score.teamBOver} overs)</p>
      <p>Winner: {score.winner}</p>
    </div>
  );
};

export default UpdateScore;
