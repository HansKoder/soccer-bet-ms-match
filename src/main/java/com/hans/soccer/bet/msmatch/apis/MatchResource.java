package com.hans.soccer.bet.msmatch.apis;

import com.hans.soccer.bet.msmatch.documents.Match;
import com.hans.soccer.bet.msmatch.documents.Team;
import com.hans.soccer.bet.msmatch.dtos.TeamDto;
import com.hans.soccer.bet.msmatch.enums.ScoreMatchEnum;
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
                .setStatusMatch(StatusMatchEnum.WAIT)
                .builder();

        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(match));
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

        if (match.getStatusMatch().equals(StatusMatchEnum.PLAY)) {
            return ResponseEntity.ok().body(Collections.singletonMap("message", "Match has status play"));
        }

        match.setStatusMatch(StatusMatchEnum.PLAY);

        Team teamA = match.getTeamA();
        teamA.setGoals(0);

        Team teamB = match.getTeamB();
        teamB.setGoals(0);

        match.setTeamA(teamA);
        match.setTeamB(teamB);

        service.save(match);

        return ResponseEntity.ok(Collections.singletonMap("message", "Start Match with successfully!"));
    }

    @PutMapping("/goal/{matchId}/{teamId}")
    ResponseEntity<?> goal (
            @PathVariable String matchId,
            @PathVariable Long teamId
    ) {
        Optional<Match> optionalMatch = service.getMatchById(matchId);

        if (optionalMatch.isEmpty()) {
            String err = "Match with the ID " + matchId + " Not found in the DB!";
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", err));
        }

        Match match = optionalMatch.get();

        if (!match.getStatusMatch().equals(StatusMatchEnum.PLAY)) {
            String err = "Match should has a status play";
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", err));
        }

        Boolean notFoundTeam = match.getTeamA().getId() != teamId && match.getTeamB().getId() != teamId;
        if (notFoundTeam) {
            String err = "Not found team with ID " + teamId;
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", err));
        }

        Team teamA = match.getTeamA();
        Team teamB = match.getTeamB();

        if (teamA.getId() == teamId) {
            teamA.setGoals(teamA.getGoals() + 1);
            match.setTeamA(teamA);
        } else {
            teamB.setGoals(teamB.getGoals() + 1);
            match.setTeamB(teamB);
        }

        service.save(match);

        return ResponseEntity.ok().body(Collections.singletonMap("message", "Add new goal with successfully"));
    }

    private Team getTeam (Match match, Long teamId) {
        if (match.getTeamA().getId() == teamId) {
            return match.getTeamA();
        }

        return match.getTeamB();
    }

    @PutMapping("/finish-match/{matchId}")
    ResponseEntity<?> finishMatch (@PathVariable String matchId) {
        Optional<Match> optionalMatch = service.getMatchById(matchId);

        if (optionalMatch.isEmpty()) {
            String err = "Match with the ID " + matchId + " Not found in the DB!";
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("error", err));
        }

        Match match = optionalMatch.get();

        if (match.getStatusMatch().equals(StatusMatchEnum.FINISH)) {
            return ResponseEntity.ok().body(Collections.singletonMap("message", "Match has status finish"));
        }

        match.setStatusMatch(StatusMatchEnum.FINISH);

        Team teamA = match.getTeamA();
        Team teamB = match.getTeamB();

        System.out.println("team A " + teamA.getGoals());
        System.out.println("team B " + teamB.getGoals());

        if (teamA.getGoals() == teamB.getGoals()) {
            teamA.setScoreMatch(ScoreMatchEnum.TIE);
            teamB.setScoreMatch(ScoreMatchEnum.TIE);
        }
        else if (teamA.getGoals() > teamB.getGoals()) {
            teamA.setScoreMatch(ScoreMatchEnum.WIN);
            teamB.setScoreMatch(ScoreMatchEnum.MISS);
        } else {
            teamA.setScoreMatch(ScoreMatchEnum.MISS);
            teamB.setScoreMatch(ScoreMatchEnum.WIN);
        }

        match.setTeamA(teamA);
        match.setTeamB(teamB);

        service.save(match);

        return ResponseEntity.ok().body(Collections.singletonMap("ok", "Match is finish with successfully"));
    }

    private Team mapTeam (TeamDto teamDto) {
        return new Team.TeamBuilder()
                .setId(teamDto.getId())
                .setTeamName(teamDto.getTeamName())
                .builder();
    }

}
