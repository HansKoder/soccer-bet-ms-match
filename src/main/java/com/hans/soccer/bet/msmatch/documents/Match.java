package com.hans.soccer.bet.msmatch.documents;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "matches")
public class Match {
    @Id
    private String id;
    private String teamA;
    private String teamB;

    public Match(String id, String teamA, String teamB) {
        this.id = id;
        this.teamA = teamA;
        this.teamB = teamB;
    }

    public Match() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamA() {
        return teamA;
    }

    public void setTeamA(String teamA) {
        this.teamA = teamA;
    }

    public String getTeamB() {
        return teamB;
    }

    public void setTeamB(String teamB) {
        this.teamB = teamB;
    }

    @Override
    public String toString() {
        return "Match{" +
                "id='" + id + '\'' +
                ", teamA='" + teamA + '\'' +
                ", teamB='" + teamB + '\'' +
                '}';
    }
}
