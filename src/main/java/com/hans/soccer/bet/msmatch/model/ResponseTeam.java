package com.hans.soccer.bet.msmatch.model;

import com.hans.soccer.bet.msmatch.dtos.TeamDTO;

public class ResponseTeam {

    private Boolean isInvalidTeam;
    private String errorMessage;
    private TeamDTO visitingTeam;
    private TeamDTO localTeam;

    public ResponseTeam(ResponseTeamBuilder builder) {
        this.isInvalidTeam = builder.isInvalidTeam;
        this.errorMessage = builder.errorMessage;
        this.localTeam = builder.localTeam;
        this.visitingTeam = builder.visitingTeam;
    }

    public Boolean getInvalidTeam() {
        return isInvalidTeam;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public TeamDTO getVisitingTeam() {
        return visitingTeam;
    }

    public TeamDTO getLocalTeam() {
        return localTeam;
    }

    public static class ResponseTeamBuilder {

        private Boolean isInvalidTeam;
        private String errorMessage;
        private TeamDTO visitingTeam;
        private TeamDTO localTeam;

        public ResponseTeamBuilder() {
            this.isInvalidTeam = Boolean.FALSE;
        }


        public ResponseTeamBuilder addIsInvalidTeam (Boolean isInvalid) {
            this.isInvalidTeam = isInvalid;
            return this;
        }

        public ResponseTeamBuilder addErrorMessage (String errorMessage) {
            this.errorMessage = errorMessage;
            return this;
        }

        public ResponseTeamBuilder addVisitingTeam (TeamDTO team) {
            this.visitingTeam = team;
            return this;
        }

        public ResponseTeamBuilder addLocalTeam (TeamDTO team) {
            this.localTeam = team;
            return this;
        }

        public ResponseTeam build () {
            return new ResponseTeam(this);
        }
    }


}
