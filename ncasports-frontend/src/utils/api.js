import axios from 'axios';


const API_URL = "http://localhost:8080";  // Ensure this matches your backend port

const api = axios.create({
  baseURL: API_URL,
  headers: {
    'Content-Type': 'application/json'
  }
});

export const fetchMatches = async () => {
  try {
    const response = await api.get('/api/matches');
    console.log('Fetched matches:', response.data); // Log fetched data here
    return response.data;
  } catch (error) {
    console.error('Error fetching matches:', error);
    throw error;
  }
};
