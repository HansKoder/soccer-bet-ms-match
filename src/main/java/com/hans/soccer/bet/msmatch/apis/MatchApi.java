package com.hans.soccer.bet.msmatch.apis;

import com.hans.soccer.bet.msmatch.dtos.MatchDto;
import com.hans.soccer.bet.msmatch.dtos.TeamDto;
import com.hans.soccer.bet.msmatch.entities.Match;
import com.hans.soccer.bet.msmatch.repositories.MatchRepository;
import com.hans.soccer.bet.msmatch.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/matches")
public class MatchApi {

    @Autowired
    private MatchService service;

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

            // Validations
            if (matchDto.getLocalTeam().getId().equals(matchDto.getVisitingTeam())) {
                String msg = "Visiting team and local team should not same";
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
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

}
