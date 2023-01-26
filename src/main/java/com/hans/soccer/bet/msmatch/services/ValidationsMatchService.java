package com.hans.soccer.bet.msmatch.services;

import com.hans.soccer.bet.msmatch.model.Response;
import com.hans.soccer.bet.msmatch.model.ResponseTeam;

public interface ValidationsMatchService {

    ResponseTeam existTeams (Long visitingId, Long localId);

    Response existTournament (Long tournamentId);

}
