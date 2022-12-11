package com.hans.soccer.bet.msmatch.repositories;

import com.hans.soccer.bet.msmatch.entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {
}
