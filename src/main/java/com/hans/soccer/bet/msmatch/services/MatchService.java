package com.hans.soccer.bet.msmatch.services;

import com.hans.soccer.bet.msmatch.documents.Match;

import java.util.List;
import java.util.Optional;

public interface MatchService {

    Match save(Match match);

    Optional<Match> getMatchById (String id);

    List<Match> getMatches ();

}
