package org.imt.tournamentmaster.controller.match;

import org.imt.tournamentmaster.model.match.Match;
import org.imt.tournamentmaster.model.match.Match.Status;
import org.imt.tournamentmaster.service.match.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/match")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Match> getById(@PathVariable long id) {
        Optional<Match> match = matchService.getById(id);

        return match.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Match> getAll() {
        return matchService.getAll();
    }

    @GetMapping("/search")
    public List<Match> search(
            @RequestParam(required = false) Long equipeAId,
            @RequestParam(required = false) Long equipeBId,
            @RequestParam(required = false) String equipeAName,
            @RequestParam(required = false) String equipeBName,
            @RequestParam(required = false) String playerName,
            @RequestParam(required = false) Status status
    ) {
        return matchService.searchMatch(equipeAId, equipeBId, equipeAName, equipeBName, playerName, status);
    }

    @PostMapping("/new")
    public ResponseEntity<Match> creerMatch(@RequestBody Match match) {
        Match nouveauMatch = matchService.creerMatch(match);
        return new ResponseEntity<>(nouveauMatch, HttpStatus.CREATED);
    }
}
