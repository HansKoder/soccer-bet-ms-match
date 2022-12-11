package com.hans.soccer.bet.msmatch.dtos;

public class TeamDto {

    private Long id;
    private String name;

    private String description;

    public TeamDto(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public TeamDto() {
    }

    public TeamDto(TeamDtoBuilder builder) {
        this.id = builder.id;
        this.description = builder.description;
        this.name = builder.name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static class TeamDtoBuilder {

        private Long id;
        private String name;
        private String description;

        public TeamDtoBuilder() {
        }

        public TeamDtoBuilder addId(Long id) {
            this.id = id;
            return this;
        }

        public TeamDtoBuilder addName(String name) {
            this.name = name;
            return this;
        }

        public TeamDtoBuilder addDescription (String description) {
            this.description = description;
            return this;
        }

        public TeamDto build () {
            TeamDto teamDto = new TeamDto(this);
            return teamDto;
        }
    }
}
