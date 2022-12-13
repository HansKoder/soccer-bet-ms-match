package com.hans.soccer.bet.msmatch.services;

import com.hans.soccer.bet.msmatch.dtos.TeamDto;

import java.util.Optional;

public interface ClientTeamService {

    Optional<TeamDto> getTeamById (Long teamId) throws Exception;

}
