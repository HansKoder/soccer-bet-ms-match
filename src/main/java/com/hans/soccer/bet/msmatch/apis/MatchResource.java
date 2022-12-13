package com.hans.soccer.bet.msmatch.apis;

import com.hans.soccer.bet.msmatch.dtos.MatchDto;
import com.hans.soccer.bet.msmatch.entities.Match;
import com.hans.soccer.bet.msmatch.model.ResponseTeam;
import com.hans.soccer.bet.msmatch.services.MatchService;
import com.hans.soccer.bet.msmatch.services.ValidateTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/matches")
public class MatchResource {

    @Autowired
    private MatchService service;

    @Autowired
    private ValidateTeamService validateTeamService;


    @GetMapping("/{matchId}")
    ResponseEntity<?> getMatch (@PathVariable Long matchId) throws Exception {
        Optional<Match> opt = service.getMatchById(matchId);

        if (opt.isEmpty()) {
            String msg = "Not found match with the ID : " + matchId;
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", msg));
        }

        Match entity = opt.get();

        ResponseTeam responseTeam = validateTeamService.validateTeams(entity.getVisitingTeam(), entity.getLocalTeam());

        if (responseTeam.getInvalidTeam()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", responseTeam.getErrorMessage()));
        }

        MatchDto matchDto = new MatchDto.MatchDtoBuilder()
                .addId(entity.getId())
                .addLocalTeam(responseTeam.getLocalTeam())
                .addVisitingTeam(responseTeam.getVisitingTeam())
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

            ResponseTeam responseTeam = validateTeamService.validateTeams(visitingId, localId);

            if (responseTeam.getInvalidTeam()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(Collections.singletonMap("error", responseTeam.getErrorMessage()));
            }

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
    ResponseEntity<?> getMatches (){

        try {
            List<MatchDto> matchDtoList = new ArrayList<>();

            List<Match> matchList = service.getMatches();

            for (Match entity : matchList) {
                ResponseTeam responseTeam = validateTeamService.validateTeams(entity.getVisitingTeam(), entity.getLocalTeam());

                if (responseTeam.getInvalidTeam()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(Collections.singletonMap("error", responseTeam.getErrorMessage()));
                }

                MatchDto matchDto = new MatchDto.MatchDtoBuilder()
                        .addId(entity.getId())
                        .addDescription(entity.getDescription())
                        .addLocalTeam(responseTeam.getLocalTeam())
                        .addVisitingTeam(responseTeam.getVisitingTeam())
                        .build();

                matchDtoList.add(matchDto);
            }

            return ResponseEntity.ok(matchDtoList);

        } catch (Exception exception) {
            System.out.println("Error get matches " + exception.getMessage());
            return ResponseEntity.internalServerError()
                    .body(Collections.singletonMap("error", exception.getMessage()));
        }
    }

}
