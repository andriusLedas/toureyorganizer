package lt.codeacademy.javau7.tournament_organizer.controllers;

import lt.codeacademy.javau7.tournament_organizer.dto.MatchDTO;
import lt.codeacademy.javau7.tournament_organizer.dto.MatchResultDTO;
import lt.codeacademy.javau7.tournament_organizer.exceptions.MatchNotFoundException;
import lt.codeacademy.javau7.tournament_organizer.models.Match;
import lt.codeacademy.javau7.tournament_organizer.models.Tournament;
import lt.codeacademy.javau7.tournament_organizer.services.MatchService;
import lt.codeacademy.javau7.tournament_organizer.services.TournamentService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/matches")
public class MatchController {
    MatchService matchService;
    TournamentService tournamentService;

    public MatchController(MatchService matchService, TournamentService tournamentService) {
        this.matchService = matchService;
        this.tournamentService = tournamentService;
    }

    @PostMapping("/generateStage1Matches/{tournamentId}")
    public ResponseEntity<String> generateStage1Matches(
            @PathVariable Long tournamentId) {
        try {
            Tournament tournament = tournamentService.getById(tournamentId);
            matchService.generateStage1Matches(tournament);
            return ResponseEntity.ok("First stage matches generated successfully for tournament ID: " + tournamentId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to generate first stage matches: " + e.getMessage());
        }
    }

    //DTO body: participant1 (string), participant2 (string)
    @PutMapping("/updateMatch/{matchId}")
    public ResponseEntity<String> updateMatch(
            @PathVariable Long matchId,
            @RequestBody MatchDTO matchDTO) {
        try {
            matchService.updateMatch(matchId, matchDTO);
            return ResponseEntity.ok("Match with ID " + matchId + " updated successfully.");
        } catch (MatchNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Match with ID " + matchId + " not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update match: " + e.getMessage());
        }
    }
    //DTO body: participant1Score (int), participant2Score (int), winner (string)
    @PutMapping("/updateResult/{matchId}")
    public ResponseEntity<String> updateMatchResult(
            @PathVariable Long matchId,
            @RequestBody MatchResultDTO resultDTO) {
        try {
            matchService.updateResult(matchId, resultDTO);
            return ResponseEntity.ok("Match result for ID " + matchId + " updated successfully.");
        } catch (MatchNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Match with ID " + matchId + " not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update match result: " + e.getMessage());
        }
    }
}

