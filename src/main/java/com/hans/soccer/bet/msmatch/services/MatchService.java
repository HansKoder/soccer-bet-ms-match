package com.hans.soccer.bet.msmatch.services;

import com.hans.soccer.bet.msmatch.entities.Match;

import java.util.List;
import java.util.Optional;

public interface MatchService {

    Match save(Match match);

    Optional<Match> getMatchById (Long id);

    List<Match> getMatches ();

}
