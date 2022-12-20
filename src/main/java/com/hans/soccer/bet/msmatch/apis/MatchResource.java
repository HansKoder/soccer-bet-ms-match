package com.hans.soccer.bet.msmatch.apis;

import com.hans.soccer.bet.msmatch.documents.Match;
import com.hans.soccer.bet.msmatch.documents.Team;
import com.hans.soccer.bet.msmatch.dtos.TeamDto;
import com.hans.soccer.bet.msmatch.dtos.UpdateBetDto;
import com.hans.soccer.bet.msmatch.enums.StatusBetEnum;
import com.hans.soccer.bet.msmatch.enums.StatusMatchEnum;
import com.hans.soccer.bet.msmatch.services.MatchService;
import com.hans.soccer.bet.msmatch.services.ValidateTeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    ResponseEntity<?> getMatch (@PathVariable String matchId) throws Exception {
        Optional<Match> opt = service.getMatchById(matchId);

        if (opt.isEmpty()) {
            String msg = "Not found match with the ID : " + matchId;
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", msg));
        }

        return ResponseEntity.ok().body(opt.get());
    }

    @GetMapping("/")
    ResponseEntity<?> getMatches (){
        try {
            List<Match> list = service.getMatches();

            if (list.isEmpty()) return ResponseEntity.noContent().build();

            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/add-teams")
    ResponseEntity<?> addTeams (@RequestBody List<TeamDto> teams) {
        if (teams.isEmpty()) return ResponseEntity.noContent().build();

        if (teams.size() != 2) {
            String msg = "Invalid number of teams";
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", msg));
        }

        Match match = new Match.MatchBuilder()
                .setTeamA(mapTeam(teams.get(0)))
                .setTeamB(mapTeam(teams.get(1)))
                .setStatusBet(StatusBetEnum.STOP)
                .setStatusMatch(StatusMatchEnum.WAIT)
                .builder();

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(match));
    }

    @PutMapping("/update-bet/{matchId}")
    ResponseEntity<?> updateBet (@PathVariable String matchId, @RequestBody UpdateBetDto updateBet) {
        Optional<Match> optionalMatch = service.getMatchById(matchId);

        if (optionalMatch.isEmpty()) {
            String err = "Match with the ID " + matchId + " Not found in the DB!";
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", err));
        }

        Match match = optionalMatch.get();

        // It modifies its bet if status match is different STOP
        StatusMatchEnum currentStatus = match.getStatusMatch();
        if (currentStatus.equals(StatusMatchEnum.PLAY) || currentStatus.equals(StatusMatchEnum.FINISH)) {
            String err = "Cannot update bet when match  has status play or finish ";
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", err));
        }

        Team teamA = new Team.TeamBuilder()
                .setId(match.getTeamA().getId())
                .setTeamName(match.getTeamA().getTeamName())
                .setBetPercentage(updateBet.getTeamABetPercentage())
                .builder();

        Team teamB = new Team.TeamBuilder()
                .setId(match.getTeamB().getId())
                .setTeamName(match.getTeamB().getTeamName())
                .setBetPercentage(updateBet.getTeamBBetPercentage())
                .builder();

        match.setTeamA(teamA);
        match.setTeamB(teamB);

        match.setStatusBet(StatusBetEnum.OPEN);

        service.save(match);

        return ResponseEntity.ok(Collections.singletonMap("message", "Update Bet with successfully!"));
    }

    @PutMapping("/start-match/{matchId}")
    ResponseEntity<?> startMatch (@PathVariable String matchId) {
        Optional<Match> optionalMatch = service.getMatchById(matchId);

        if (optionalMatch.isEmpty()) {
            String err = "Match with the ID " + matchId + " Not found in the DB!";
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", err));
        }

        Match match = optionalMatch.get();

        match.setStatusMatch(StatusMatchEnum.PLAY);
        match.setStatusBet(StatusBetEnum.STOP);

        service.save(match);

        return ResponseEntity.ok(Collections.singletonMap("message", "Start Match with successfully!"));
    }


    private Team mapTeam (TeamDto teamDto) {
        return new Team.TeamBuilder()
                .setId(teamDto.getId())
                .setTeamName(teamDto.getTeamName())
                .builder();
    }


}
