import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom';

const MatchList = () => {
  const [matchesByTournament, setMatchesByTournament] = useState({});
  const [loading, setLoading] = useState(true);  // Added loading state
  const [error, setError] = useState(null);      // Added error state

  useEffect(() => {
    const fetchMatches = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/matches');
        console.log("Fetched matches:", response.data);

        // Group matches by tournament name
        const groupedMatches = response.data.reduce((acc, match) => {
          const { tournamentName } = match;
          if (!acc[tournamentName]) acc[tournamentName] = [];
          acc[tournamentName].push(match);
          return acc;
        }, {});

        setMatchesByTournament(groupedMatches);
      } catch (error) {
        console.error('Error fetching matches:', error);
        setError('Failed to load matches. Please try again later.'); // Set error message
      } finally {
        setLoading(false); // Set loading to false once the API call is done
      }
    };

    fetchMatches();
  }, []);

  // Show loading spinner while fetching data
  if (loading) {
    return <div>Loading...</div>;
  }

  // Show error message if there's an error
  if (error) {
    return <div style={{ color: 'red' }}>{error}</div>;
  }

  return (
    <div>
      <h2>NCA Sports</h2>
      <h3>Match List</h3>
      {Object.entries(matchesByTournament).map(([tournamentName, matches]) => (
        <div key={tournamentName}>
          <h3>{tournamentName}</h3>
          <ul>
            {matches.map((match) => (
              <li key={match.id}>
                <div>
                  {match.teamA} vs {match.teamB} - Scheduled on{' '}
                  {new Date(match.scheduledDate).toLocaleString()}
                  <Link to={`/match/${match.id}`}>Match Details</Link>
                </div>
              </li>
            ))}
          </ul>
        </div>
      ))}
    </div>
  );
};

export default MatchList;
