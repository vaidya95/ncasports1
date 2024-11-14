import React, { useState } from 'react';
import axios from 'axios';

const AddTournament = ({ onTournamentAdded }) => {
    const [name, setName] = useState('');
    const [location, setLocation] = useState('');
    const [startDate, setStartDate] = useState('');
    const [endDate, setEndDate] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/api/tournaments', {
                name,
                location,
                startDate,
                endDate
            });
            console.log('Tournament created:', response.data);
            onTournamentAdded(response.data); // Call the callback to refresh the tournament list
            setName('');
            setLocation('');
            setStartDate('');
            setEndDate('');
        } catch (error) {
            console.error('Error creating tournament:', error);
        }
    };

    return (
        <div>
            <h3>Add New Tournament</h3>
            <form onSubmit={handleSubmit}>
                <input type="text" placeholder="Tournament Name" value={name} onChange={(e) => setName(e.target.value)} required />
                <input type="text" placeholder="Location" value={location} onChange={(e) => setLocation(e.target.value)} required />
                <input type="date" placeholder="Start Date" value={startDate} onChange={(e) => setStartDate(e.target.value)} required />
                <input type="date" placeholder="End Date" value={endDate} onChange={(e) => setEndDate(e.target.value)} required />
                <button type="submit">Add Tournament</button>
            </form>
        </div>
    );
};

export default AddTournament;
