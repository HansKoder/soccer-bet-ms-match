package com.hans.soccer.bet.msmatch.clients;

import com.hans.soccer.bet.msmatch.dtos.TournamentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class TournamentClientImpl implements TournamentClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public Optional<TournamentDTO> findTournamentById(Long tournamentId) {
        try {
            TournamentDTO response = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8095/api/tournaments/" + tournamentId )
                    .retrieve()
                    .bodyToMono(TournamentDTO.class)
                    .block();

            return Optional.of(response);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }
}
