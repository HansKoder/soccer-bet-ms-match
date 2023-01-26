package com.hans.soccer.bet.msmatch.services;

import com.hans.soccer.bet.msmatch.clients.TeamClient;
import com.hans.soccer.bet.msmatch.clients.TournamentClient;
import com.hans.soccer.bet.msmatch.dtos.TeamDTO;
import com.hans.soccer.bet.msmatch.dtos.TournamentDTO;
import com.hans.soccer.bet.msmatch.model.Response;
import com.hans.soccer.bet.msmatch.model.ResponseTeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ValidationsMatchServiceImpl implements ValidationsMatchService {

    @Autowired
    private TeamClient teamClient;

    @Autowired
    private TournamentClient tournamentClient;

    @Override
    public ResponseTeam existTeams(Long visitingId, Long localId) {
        try {
            Optional<TeamDTO> visiting = teamClient.getTeamById(visitingId);
            if (visiting.isEmpty()) {
                return new ResponseTeam.ResponseTeamBuilder()
                        .addIsInvalidTeam(Boolean.TRUE)
                        .addErrorMessage("Visiting team not found")
                        .build();
            }

            Optional<TeamDTO> local = teamClient.getTeamById(localId);
            if (local.isEmpty()) {
                return new ResponseTeam.ResponseTeamBuilder()
                        .addIsInvalidTeam(Boolean.TRUE)
                        .addErrorMessage("Local team not found")
                        .build();
            }

            return new ResponseTeam.ResponseTeamBuilder()
                    .addVisitingTeam(visiting.get())
                    .addLocalTeam(local.get()).build();
        } catch (Exception e) {
            return new ResponseTeam.ResponseTeamBuilder()
                    .addIsInvalidTeam(Boolean.TRUE)
                    .addErrorMessage("Error " + e.getMessage())
                    .build();
        }
    }

    @Override
    public Response existTournament(Long tournamentId) {
        try {
            Optional<TournamentDTO> optional = tournamentClient.findTournamentById(tournamentId);

            if (optional.isEmpty()) {
                return new Response.ResponseBuilder()
                        .addIsError(Boolean.TRUE)
                        .addErrorMessage("Tournament with the ID " + tournamentId + " not found in own Database")
                        .build();
            }

            return new Response.ResponseBuilder()
                    .addIsError(Boolean.FALSE)
                    .addData(optional.get())
                    .build();
        } catch (Exception e) {
            return new Response.ResponseBuilder()
                    .addIsError(Boolean.TRUE)
                    .addErrorMessage("Error " + e.getMessage())
                    .build();
        }
    }
}
