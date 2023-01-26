package com.hans.soccer.bet.msmatch.clients;

import com.hans.soccer.bet.msmatch.dtos.TeamDTO;

import java.util.Optional;

public interface TeamClient {

    Optional<TeamDTO> getTeamById (Long teamId);

}
