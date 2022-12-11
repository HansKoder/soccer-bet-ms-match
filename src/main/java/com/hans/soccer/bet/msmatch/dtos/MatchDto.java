package com.hans.soccer.bet.msmatch.dtos;

public class MatchDto {

    private Long id;

    private TeamDto visitingTeam;

    private TeamDto localTeam;

    private String description;

    public MatchDto() {
    }

    public MatchDto(Long id, TeamDto visitingTeam, TeamDto localTeam, String description) {
        this.id = id;
        this.visitingTeam = visitingTeam;
        this.localTeam = localTeam;
        this.description = description;
    }

    public MatchDto(MatchDtoBuilder builder) {
        this.id = builder.id;
        this.visitingTeam = builder.visitingTeam;
        this.localTeam = builder.localTeam;
        this.description = builder.description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TeamDto getVisitingTeam() {
        return visitingTeam;
    }

    public void setVisitingTeam(TeamDto visitingTeam) {
        this.visitingTeam = visitingTeam;
    }

    public TeamDto getLocalTeam() {
        return localTeam;
    }

    public void setLocalTeam(TeamDto localTeam) {
        this.localTeam = localTeam;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class MatchDtoBuilder  {
        private Long id;

        private TeamDto visitingTeam;

        private TeamDto localTeam;

        private String description;

        public MatchDtoBuilder() {
        }

        public MatchDtoBuilder addId(Long id) {
            this.id = id;
            return this;
        }

        public MatchDtoBuilder addVisitingTeam(TeamDto visitingTeam) {
            this.visitingTeam = visitingTeam;
            return this;
        }

        public MatchDtoBuilder addLocalTeam(TeamDto localTeam) {
            this.localTeam = localTeam;
            return this;
        }

        public MatchDtoBuilder addDescription(String description) {
            this.description = description;
            return this;
        }

        public MatchDto build () {
            return new MatchDto(this);
        }
    }
}
