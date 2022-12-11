package com.hans.soccer.bet.msmatch.entities;

import com.hans.soccer.bet.msmatch.dtos.MatchDto;
import com.hans.soccer.bet.msmatch.dtos.TeamDto;
import jakarta.persistence.*;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long visitingTeam;

    private Long localTeam;

    private String description;

    public Match() {
    }

    public Match(Long id, Long visitingTeam, Long localTeam, String description) {
        this.id = id;
        this.visitingTeam = visitingTeam;
        this.localTeam = localTeam;
        this.description = description;
    }

    public Match(MatchBuilder builder) {
        this.id = builder.id;
        this.description = builder.description;
        this.localTeam = builder.localTeam;
        this.visitingTeam = builder.visitingTeam;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getVisitingTeam() {
        return visitingTeam;
    }

    public void setVisitingTeam(Long visitingTeam) {
        this.visitingTeam = visitingTeam;
    }

    public Long getLocalTeam() {
        return localTeam;
    }

    public void setLocalTeam(Long localTeam) {
        this.localTeam = localTeam;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public static class MatchBuilder  {
        private Long id;

        private Long visitingTeam;

        private Long localTeam;

        private String description;

        public MatchBuilder() {
        }

        public MatchBuilder addId(Long id) {
            this.id = id;
            return this;
        }

        public MatchBuilder addVisitingTeam(Long visitingTeam) {
            this.visitingTeam = visitingTeam;
            return this;
        }

        public MatchBuilder addLocalTeam(Long localTeam) {
            this.localTeam = localTeam;
            return this;
        }

        public MatchBuilder addDescription(String description) {
            this.description = description;
            return this;
        }

        public Match build () {
            return new Match(this);
        }
    }
}
