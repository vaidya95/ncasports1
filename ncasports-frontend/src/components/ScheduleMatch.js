import React, { useEffect, useState } from 'react';
import axios from 'axios';

const ScheduleMatch = () => {
  const [tournaments, setTournaments] = useState([]);
  const [teamA, setTeamA] = useState('');
  const [teamB, setTeamB] = useState('');
  const [tournamentId, setTournamentId] = useState('');
  const [scheduledDate, setScheduledDate] = useState('');
  const [tossWinner, setTossWinner] = useState('');
  const [tossDecision, setTossDecision] = useState('');

  // State to store team players dynamically
  const [teamAPlayers, setTeamAPlayers] = useState([]);
  const [teamBPlayers, setTeamBPlayers] = useState([]);

  useEffect(() => {
    // Fetch tournaments from the backend
    axios
      .get('http://localhost:8080/api/tournaments')
      .then((response) => {
        setTournaments(response.data);
      })
      .catch((error) => {
        console.error('Error fetching tournaments:', error);
      });
  }, []);

  const handleAddPlayer = (team) => {
    const newPlayer = { playerName: '', role: 'BATSMAN' }; // Default role as BATSMAN
    if (team === 'teamA') {
      setTeamAPlayers([...teamAPlayers, newPlayer]);
    } else {
      setTeamBPlayers([...teamBPlayers, newPlayer]);
    }
  };

  const handlePlayerChange = (index, team, playerName, role) => {
    const updatedPlayers = team === 'teamA' ? [...teamAPlayers] : [...teamBPlayers];
    updatedPlayers[index] = { playerName, role };
    team === 'teamA' ? setTeamAPlayers(updatedPlayers) : setTeamBPlayers(updatedPlayers);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const matchData = {
      teamA,
      teamB,
      scheduledDate,
      tossWinner,
      tossDecision,
      tournamentId,
      teamAPlayers,
      teamBPlayers,
    };

    try {
      const response = await axios.post('http://localhost:8080/api/matches', matchData);
      console.log('Match scheduled successfully:', response.data);
    } catch (error) {
      console.error('Error scheduling match:', error);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      {/* Tournament Selection */}
      <div>
        <label>Tournament:</label>
        <select
          value={tournamentId}
          onChange={(e) => setTournamentId(e.target.value)}
        >
          <option value="">Select Tournament</option>
          {tournaments.map((tournament) => (
            <option key={tournament.id} value={tournament.id}>
              {tournament.name}
            </option>
          ))}
        </select>
      </div>

      {/* Team A and Team B */}
      <div>
        <label>Team A:</label>
        <input
          type="text"
          value={teamA}
          onChange={(e) => setTeamA(e.target.value)}
        />
      </div>

      <div>
        <label>Team B:</label>
        <input
          type="text"
          value={teamB}
          onChange={(e) => setTeamB(e.target.value)}
        />
      </div>

      {/* Scheduled Date */}
      <div>
        <label>Scheduled Date:</label>
        <input
          type="datetime-local"
          value={scheduledDate}
          onChange={(e) => setScheduledDate(e.target.value)}
        />
      </div>

      {/* Toss Winner */}
      <div>
        <label>Toss Winner:</label>
        <input
          type="text"
          value={tossWinner}
          onChange={(e) => setTossWinner(e.target.value)}
        />
      </div>

      {/* Toss Decision */}
      <div>
        <label>Toss Decision:</label>
        <select
          value={tossDecision}
          onChange={(e) => setTossDecision(e.target.value)}
        >
          <option value="">Select Decision</option>
          <option value="Batting">Batting</option>
          <option value="Bowling">Bowling</option>
        </select>
      </div>

      {/* Team A Players */}
      <div>
        <h3>Team A Players</h3>
        {teamAPlayers.map((player, index) => (
          <div key={index}>
            <label>Player {index + 1} Name:</label>
            <input
              type="text"
              value={player.playerName}
              onChange={(e) =>
                handlePlayerChange(index, 'teamA', e.target.value, player.role)
              }
            />
            <select
              value={player.role}
              onChange={(e) =>
                handlePlayerChange(index, 'teamA', player.playerName, e.target.value)
              }
            >
              <option value="BATSMAN">BATSMAN</option>
              <option value="BOWLER">BOWLER</option>
              <option value="ALLROUNDER">ALLROUNDER</option>
              <option value="WICKETKEEPER">WICKETKEEPER</option> {/* Added WICKETKEEPER option */}
            </select>
          </div>
        ))}
        {/* Add a new player */}
        <button type="button" onClick={() => handleAddPlayer('teamA')}>
          Add Player to Team A
        </button>
      </div>

      {/* Team B Players */}
      <div>
        <h3>Team B Players</h3>
        {teamBPlayers.map((player, index) => (
          <div key={index}>
            <label>Player {index + 1} Name:</label>
            <input
              type="text"
              value={player.playerName}
              onChange={(e) =>
                handlePlayerChange(index, 'teamB', e.target.value, player.role)
              }
            />
            <select
              value={player.role}
              onChange={(e) =>
                handlePlayerChange(index, 'teamB', player.playerName, e.target.value)
              }
            >
              <option value="BATSMAN">BATSMAN</option>
              <option value="BOWLER">BOWLER</option>
              <option value="ALLROUNDER">ALLROUNDER</option>
              <option value="WICKETKEEPER">WICKETKEEPER</option> {/* Added WICKETKEEPER option */}
            </select>
          </div>
        ))}
        {/* Add a new player */}
        <button type="button" onClick={() => handleAddPlayer('teamB')}>
          Add Player to Team B
        </button>
      </div>

      {/* Submit */}
      <button type="submit">Schedule Match</button>
    </form>
  );
};

export default ScheduleMatch;
