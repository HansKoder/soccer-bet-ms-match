package com.hans.soccer.bet.msmatch.apis;

import com.hans.soccer.bet.msmatch.dtos.MatchDto;
import com.hans.soccer.bet.msmatch.documents.Match;
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

    @PostMapping("/")
    ResponseEntity<?> saveMatch (@RequestBody Match entity) {
        Match resp = service.save(entity);
        System.out.println(resp);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(entity));
    }

    @GetMapping("/")
    ResponseEntity<?> getMatches (){
        try {
            System.out.println("getMatches..");
            List<Match> list = service.getMatches();

            if (list.isEmpty()) return ResponseEntity.noContent().build();

            list.forEach(m -> {
                System.out.println(m);
            });

            return ResponseEntity.ok(list);
        } catch (Exception e) {
            String msg = e.getMessage();

            return ResponseEntity.internalServerError().body(Collections.singletonMap("error", msg));
        }
    }

}
