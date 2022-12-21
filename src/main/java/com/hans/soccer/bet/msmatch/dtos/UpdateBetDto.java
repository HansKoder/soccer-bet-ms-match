package com.hans.soccer.bet.msmatch.dtos;

public class UpdateBetDto {

    private String teamAId;
    private Double teamABetPercentage;

    private String teamBId;

    private Double teamBBetPercentage;

    public UpdateBetDto(String teamAId, Double teamABetPercentage, String teamBId, Double teamBBetPercentage) {
        this.teamAId = teamAId;
        this.teamABetPercentage = teamABetPercentage;
        this.teamBId = teamBId;
        this.teamBBetPercentage = teamBBetPercentage;
    }

    public String getTeamAId() {
        return teamAId;
    }

    public void setTeamAId(String teamAId) {
        this.teamAId = teamAId;
    }

    public Double getTeamABetPercentage() {
        return teamABetPercentage;
    }

    public void setTeamABetPercentage(Double teamABetPercentage) {
        this.teamABetPercentage = teamABetPercentage;
    }

    public String getTeamBId() {
        return teamBId;
    }

    public void setTeamBId(String teamBId) {
        this.teamBId = teamBId;
    }

    public Double getTeamBBetPercentage() {
        return teamBBetPercentage;
    }

    public void setTeamBBetPercentage(Double teamBBetPercentage) {
        this.teamBBetPercentage = teamBBetPercentage;
    }
}
