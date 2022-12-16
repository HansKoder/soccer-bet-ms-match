package com.hans.soccer.bet.msmatch.services;

import com.hans.soccer.bet.msmatch.documents.Match;
import com.hans.soccer.bet.msmatch.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchRepository repository;

    @Override
    public Match save(Match match) {
        return repository.save(match);
    }

    @Override
    public Optional<Match> getMatchById(String id) {
        return repository.findById(id);
    }

    @Override
    public List<Match> getMatches() {
        return repository.findAll();
    }
}
