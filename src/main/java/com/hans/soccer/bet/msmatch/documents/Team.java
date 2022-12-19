package com.hans.soccer.bet.msmatch.documents;

import com.hans.soccer.bet.msmatch.enums.ScoreMatchEnum;
import com.hans.soccer.bet.msmatch.enums.StatusBetEnum;

public class Team {

    private Long id;
    private String teamName;

    private Long betPercentage;

    private ScoreMatchEnum scoreMatch;

    private Integer goals;

    public Team(TeamBuilder builder) {
        id = builder.id;
        teamName = builder.teamName;
        betPercentage = builder.betPercentage;
        scoreMatch = builder.scoreMatch;
        goals = builder.goals;
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

    public static class TeamBuilder {
        private Long id;
        private String teamName;

        private Long betPercentage;


        private ScoreMatchEnum scoreMatch;

        private Integer goals;

        public TeamBuilder setId (Long id) {
            this.id = id;
            return this;
        }

        public TeamBuilder setTeamName (String teamName) {
            this.teamName = teamName;
            return this;
        }

        public TeamBuilder setBetPercentage (Long percentage) {
            this.betPercentage = percentage;
            return this;
        }

        public TeamBuilder setScoreMatch (ScoreMatchEnum scoreMatch) {
            this.scoreMatch = scoreMatch;
            return this;
        }

        public TeamBuilder setGoals (Integer goals) {
            this.goals = goals;
            return this;
        }

        public Team builder () {
            return new Team(this);
        }
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", teamName='" + teamName + '\'' +
                '}';
    }
}
