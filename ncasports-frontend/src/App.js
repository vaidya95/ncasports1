import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useNavigate, Routes, Route, useParams } from 'react-router-dom'; // Add useParams for extracting matchId
import AddTournament from './components/AddTournament';
import ScheduleMatch from './components/ScheduleMatch';
import MatchDetails from './components/MatchDetails';
import UpdateScore from './components/UpdateScore';

const App = () => {
  const [matchesByTournament, setMatchesByTournament] = useState({});
  const [tournaments, setTournaments] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    fetchMatches();
    fetchTournaments();
  }, []);

  const fetchTournaments = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/tournaments');
      setTournaments(response.data);
    } catch (error) {
      console.error('Error fetching tournaments:', error);
    }
  };

  const fetchMatches = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/matches');
      const groupedMatches = response.data.reduce((acc, match) => {
        const { tournamentName } = match;
        if (!acc[tournamentName]) acc[tournamentName] = [];
        acc[tournamentName].push(match);
        return acc;
      }, {});
      setMatchesByTournament(groupedMatches);
    } catch (error) {
      console.error('Error fetching matches:', error);
    }
  };

  const handleTournamentAdded = (newTournament) => {
    setTournaments((prevTournaments) => [...prevTournaments, newTournament]);
  };

  const handleMatchScheduled = () => {
    fetchMatches(); // Refresh matches after scheduling a new match
  };

  // Handle view match details
  const handleViewDetails = (matchId) => {
    navigate(`/match/${matchId}`);
  };

  // Handle update score
  const handleUpdateScore = (matchId) => {
    navigate(`/update-score/${matchId}`);
  };

  return (
    <div>
      <h2>NCA Sports</h2>
      <AddTournament onTournamentAdded={handleTournamentAdded} />
      <ScheduleMatch tournaments={tournaments} onMatchScheduled={handleMatchScheduled} />

      <h3>Match List</h3>
      {Object.entries(matchesByTournament).map(([tournamentName, matches]) => (
        <div key={tournamentName}>
          <h3>{tournamentName}</h3>
          <ul>
            {matches.map((match) => (
              <li key={match.id}>
                <div>
                  {match.teamA} vs {match.teamB} - Scheduled on{' '}
                  {new Date(match.scheduledDate).toLocaleString('en-GB', {
                    day: '2-digit',
                    month: '2-digit',
                    year: 'numeric',
                    hour: '2-digit',
                    minute: '2-digit',
                    second: '2-digit',
                  })}
                  <button onClick={() => handleViewDetails(match.id)}>Match Details</button>
                  <button onClick={() => handleUpdateScore(match.id)}>Update Score</button>
                </div>
              </li>
            ))}
          </ul>
        </div>
      ))}

      {/* Routes */}
      <Routes>
        <Route path="/match/:matchId" element={<MatchDetails />} />
        <Route path="/update-score/:matchId" element={<UpdateScore />} />
      </Routes>
    </div>
  );
};

export default App;
