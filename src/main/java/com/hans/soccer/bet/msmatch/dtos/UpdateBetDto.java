package com.hans.soccer.bet.msmatch.dtos;

public class UpdateBetDto {

    private String teamAId;
    private Long teamABetPercentage;

    private String teamBId;

    private Long teamBBetPercentage;

    public UpdateBetDto(String teamAId, Long teamABetPercentage, String teamBId, Long teamBBetPercentage) {
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

    public Long getTeamABetPercentage() {
        return teamABetPercentage;
    }

    public void setTeamABetPercentage(Long teamABetPercentage) {
        this.teamABetPercentage = teamABetPercentage;
    }

    public String getTeamBId() {
        return teamBId;
    }

    public void setTeamBId(String teamBId) {
        this.teamBId = teamBId;
    }

    public Long getTeamBBetPercentage() {
        return teamBBetPercentage;
    }

    public void setTeamBBetPercentage(Long teamBBetPercentage) {
        this.teamBBetPercentage = teamBBetPercentage;
    }
}
