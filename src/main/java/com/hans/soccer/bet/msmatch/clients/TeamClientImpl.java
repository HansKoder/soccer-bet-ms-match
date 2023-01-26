package com.hans.soccer.bet.msmatch.clients;

import com.hans.soccer.bet.msmatch.dtos.TeamDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class TeamClientImpl implements TeamClient {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public Optional<TeamDTO> getTeamById(Long teamId) {
        try {
            TeamDTO teamDto = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8095/api/football-teams/" + teamId )
                    .retrieve()
                    .bodyToMono(TeamDTO.class)
                    .block();

            return Optional.of(teamDto);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }
}
