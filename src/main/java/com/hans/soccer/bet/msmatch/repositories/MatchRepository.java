package com.hans.soccer.bet.msmatch.repositories;

import com.hans.soccer.bet.msmatch.documents.Match;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchRepository extends MongoRepository<Match, String> {
}
