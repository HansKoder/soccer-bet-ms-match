package com.hans.soccer.bet.msmatch.services;

import com.hans.soccer.bet.msmatch.dtos.TeamDto;
import com.hans.soccer.bet.msmatch.model.ResponseTeam;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class ValidateTeamServiceImpl implements ValidateTeamService{

    @Autowired
    private ClientTeamService clientTeamService;

    @Override
    public ResponseTeam validateTeams(Long visitingId, Long localId) throws Exception {
            Optional<TeamDto> visiting = clientTeamService.getTeamById(visitingId);
            if (visiting.isEmpty()) {
                return new ResponseTeam.ResponseTeamBuilder()
                        .addIsInvalidTeam(Boolean.TRUE)
                        .addErrorMessage("Visiting team not found")
                        .build();
            }

            Optional<TeamDto> local = clientTeamService.getTeamById(localId);
            if (local.isEmpty()) {
                return new ResponseTeam.ResponseTeamBuilder()
                        .addIsInvalidTeam(Boolean.TRUE)
                        .addErrorMessage("Local team not found")
                        .build();
            }

        return new ResponseTeam.ResponseTeamBuilder()
                .addVisitingTeam(visiting.get())
                .addLocalTeam(local.get()).build();

    }
}
