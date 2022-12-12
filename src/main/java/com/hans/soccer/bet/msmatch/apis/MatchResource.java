package com.hans.soccer.bet.msmatch.apis;

import com.hans.soccer.bet.msmatch.dtos.MatchDto;
import com.hans.soccer.bet.msmatch.dtos.TeamDto;
import com.hans.soccer.bet.msmatch.entities.Match;
import com.hans.soccer.bet.msmatch.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/matches")
public class MatchResource {

    @Autowired
    private MatchService service;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @GetMapping("/{matchId}")
    ResponseEntity<?> getMatch (@PathVariable Long matchId) {
        Optional<Match> opt = service.getMatchById(matchId);

        if (opt.isEmpty()) {
            String msg = "Not found match with the ID : " + matchId;
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", msg));
        }

        Match entity = opt.get();

        TeamDto visiting = new TeamDto.TeamDtoBuilder().addId(entity.getVisitingTeam()).addName("visiting").build();
        TeamDto local = new TeamDto.TeamDtoBuilder().addId(entity.getLocalTeam()).addName("local").build();

        MatchDto matchDto = new MatchDto.MatchDtoBuilder()
                .addId(entity.getId())
                .addLocalTeam(local)
                .addVisitingTeam(visiting)
                .addDescription(entity.getDescription())
                .build();

        return ResponseEntity.ok(matchDto);
    }

    @PostMapping("/")
    ResponseEntity<?> saveMatch (@RequestBody MatchDto matchDto) {
        try {

            Long visitingId = matchDto.getVisitingTeam().getId();
            Long localId = matchDto.getLocalTeam().getId();

            // Validations
            if (visitingId.equals(localId)) {
                String msg = "Visiting team and local team should not same";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections.singletonMap("error", msg));
            }

            Optional<TeamDto> visiting = getTeamByWebClient(visitingId);
            if (visiting.isEmpty()) {
                String msg = "Visiting team not found";
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", msg));
            }

            Optional<TeamDto> local = getTeamByWebClient(localId);
            if (local.isEmpty()) {
                String msg = "Local team not found";
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", msg));
            }

            // Mapper DTO to Entity
            Match entity = new Match.MatchBuilder()
                    .addId(matchDto.getId())
                    .addDescription(matchDto.getDescription())
                    .addLocalTeam(matchDto.getLocalTeam().getId())
                    .addVisitingTeam(matchDto.getVisitingTeam().getId())
                    .build();

            return ResponseEntity.status(HttpStatus.CREATED).body(service.save(entity));

        } catch (Exception exception) {
            String error = exception.getMessage();

            System.out.println("Error : " + error);

            return ResponseEntity.internalServerError()
                    .body(Collections.singletonMap("error", error));

        }

    }

    @GetMapping("/")
    ResponseEntity<?> getMatches () {
        return ResponseEntity.ok(service.getMatches());
    }

    private Optional<TeamDto> getTeamByWebClient (Long teamId) {
        try {
            TeamDto teamDto = webClientBuilder.build()
                    .get()
                    .uri("http://localhost:8095/api/football-teams/" + teamId )
                    .retrieve()
                    .bodyToMono(TeamDto.class)
                    .block();

            return Optional.of(teamDto);
        } catch (Exception exception) {
            System.out.println("Error " + exception.getMessage());

            return Optional.empty();
        }
    }

}
