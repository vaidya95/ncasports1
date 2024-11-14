 package com.cricket.ncasports.util;

import com.cricket.ncasports.dto.MatchDTO;
import com.cricket.ncasports.model.Match;

public class DtoConverter {

    public static MatchDTO convertToMatchDTO(Match match) {
        MatchDTO matchDTO = new MatchDTO();
        matchDTO.setId(match.getId());
        matchDTO.setTeamA(match.getTeamA());
        matchDTO.setTeamB(match.getTeamB());
        matchDTO.setScheduledDate(match.getScheduledDate());
        matchDTO.setTournamentId(match.getTournament().getId());
        matchDTO.setTournamentName(match.getTournament().getName());
        return matchDTO;
    }
}
