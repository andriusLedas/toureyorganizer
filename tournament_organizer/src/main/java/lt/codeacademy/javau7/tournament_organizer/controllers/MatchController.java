package lt.codeacademy.javau7.tournament_organizer.controllers;

import lt.codeacademy.javau7.tournament_organizer.models.Tournament;
import lt.codeacademy.javau7.tournament_organizer.services.MatchService;
import lt.codeacademy.javau7.tournament_organizer.services.TournamentService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/matches")
public class MatchController {
    MatchService matchService;
    TournamentService tournamentService;

    public MatchController(MatchService matchService, TournamentService tournamentService) {
        this.matchService = matchService;
        this.tournamentService = tournamentService;
    }

    @PostMapping("/generateFirstStageMatches/{tournamentId}")
    public ResponseEntity<String> generateFirstStageMatches(
            @PathVariable Long tournamentId) {
        try {
            Tournament tournament = tournamentService.getById(tournamentId);
            matchService.generateFirstStageMatches(tournament);
            return ResponseEntity.ok("First stage matches generated successfully for tournament ID: " + tournamentId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to generate first stage matches: " + e.getMessage());
        }
    }
}

