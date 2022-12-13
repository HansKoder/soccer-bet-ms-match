package com.hans.soccer.bet.msmatch.services;

import com.hans.soccer.bet.msmatch.model.ResponseTeam;

public interface ValidateTeamService {

    ResponseTeam validateTeams (Long visitingId, Long localId) throws Exception;

}
