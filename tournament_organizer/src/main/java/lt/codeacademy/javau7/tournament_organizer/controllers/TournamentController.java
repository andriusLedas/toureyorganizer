package lt.codeacademy.javau7.tournament_organizer.controllers;


import lt.codeacademy.javau7.tournament_organizer.models.Tournament;
import lt.codeacademy.javau7.tournament_organizer.services.TournamentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<String> createTournament(
            @PathVariable Long userId,
            @RequestBody Tournament tournament) {

        try {
            tournamentService.createTournament(userId, tournament);
            return ResponseEntity.ok("Tournament created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create tournament: " + e.getMessage());
        }
    }

    @GetMapping("/userTournaments/{userId}")
    public ResponseEntity<?> getAllUserTournaments(@PathVariable Long userId) {
        try {
            List<Tournament> tournaments = tournamentService.getAllUserTournaments(userId);

            if (tournaments.isEmpty()) {
                return ResponseEntity.ok("No tournaments found for the user.");
            } else {
                return ResponseEntity.ok(tournaments);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve tournaments: " + e.getMessage());
        }
    }
}
