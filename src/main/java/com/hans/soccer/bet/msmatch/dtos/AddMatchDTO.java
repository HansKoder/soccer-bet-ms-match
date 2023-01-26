package com.hans.soccer.bet.msmatch.dtos;

import java.util.List;

public class AddMatchDTO {

    List<TeamDto> teams;

    TournamentDTO tournament;

    public AddMatchDTO(List<TeamDto> teams, TournamentDTO tournament) {
        this.teams = teams;
        this.tournament = tournament;
    }

    public AddMatchDTO() {
    }

    public List<TeamDto> getTeams() {
        return teams;
    }

    public void setTeams(List<TeamDto> teams) {
        this.teams = teams;
    }

    public TournamentDTO getTournament() {
        return tournament;
    }

    public void setTournament(TournamentDTO tournament) {
        this.tournament = tournament;
    }
}
