package com.hans.soccer.bet.msmatch.dtos;

import java.util.List;

public class AddMatchDTO {

    List<Long> teams;

    Long tournamentId;

    public AddMatchDTO(List<Long> teams, Long tournamentId) {
        this.teams = teams;
        this.tournamentId = tournamentId;
    }

    public AddMatchDTO() {
    }

    public List<Long> getTeams() {
        return teams;
    }

    public void setTeams(List<Long> teams) {
        this.teams = teams;
    }

    public Long getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Long tournamentId) {
        this.tournamentId = tournamentId;
    }
}
