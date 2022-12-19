package com.hans.soccer.bet.msmatch.dtos;

public class TeamDto {

    private Long id;
    private String teamName;

    public TeamDto(Long id, String teamName) {
        this.id = id;
        this.teamName = teamName;
    }

    public TeamDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
