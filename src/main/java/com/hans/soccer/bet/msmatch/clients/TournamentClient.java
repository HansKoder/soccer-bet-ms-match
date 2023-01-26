package com.hans.soccer.bet.msmatch.clients;

import com.hans.soccer.bet.msmatch.dtos.TournamentDTO;

import java.util.Optional;

public interface TournamentClient {

    Optional<TournamentDTO> findTournamentById (Long tournamentId);

}
