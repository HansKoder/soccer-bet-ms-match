package com.hans.soccer.bet.msmatch.services;

import com.hans.soccer.bet.msmatch.dtos.TeamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

@Service
public class ClientTeamServiceImpl implements ClientTeamService{

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Override
    public Optional<TeamDto> getTeamById(Long teamId) throws Exception {
        try {
            TeamDto teamDto = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8095/api/football-teams/" + teamId )
                    .retrieve()
                    .bodyToMono(TeamDto.class)
                    .block();

            return Optional.of(teamDto);
        } catch (Exception exception) {
            return Optional.empty();
        }
    }
}
