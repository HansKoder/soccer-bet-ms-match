package com.hans.soccer.bet.msmatch.documents;

import com.hans.soccer.bet.msmatch.enums.StatusBetEnum;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "matches")
public class Match {
    @Id
    private String id;
    private Team teamA;
    private Team teamB;
    private StatusBetEnum statusBet;


    public Match(MatchBuilder builder) {
        this.teamA = builder.teamA;
        this.teamB = builder.teamB;
        this.statusBet = builder.statusBet;
    }

    public Match() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Team getTeamA() {
        return teamA;
    }

    public void setTeamA(Team teamA) {
        this.teamA = teamA;
    }

    public Team getTeamB() {
        return teamB;
    }

    public void setTeamB(Team teamB) {
        this.teamB = teamB;
    }

    public StatusBetEnum getStatusBet() {
        return statusBet;
    }

    public void setStatusBet(StatusBetEnum statusBet) {
        this.statusBet = statusBet;
    }

    public static class MatchBuilder {
        private Team teamA;
        private Team teamB;
        private StatusBetEnum statusBet;

        public MatchBuilder setTeamA (Team team) {
            this.teamA = team;
            return this;
        }

        public MatchBuilder setTeamB (Team team) {
            this.teamB = team;
            return this;
        }

        public MatchBuilder setStatusBet (StatusBetEnum statusBet) {
            this.statusBet = statusBet;
            return this;
        }

        public Match builder () {
            return new Match(this);
        }
    }

    @Override
    public String toString() {
        return "Match{" +
                "id='" + id + '\'' +
                ", teamA=" + teamA +
                ", teamB=" + teamB +
                ", statusBet=" + statusBet +
                '}';
    }
}