package lt.codeacademy.javau7.tournament_organizer.controllers;

import lt.codeacademy.javau7.tournament_organizer.exceptions.TournamentNotFoundException;
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

    @GetMapping("/get/{tournamentId}")
    public ResponseEntity<?> getTournamentById(@PathVariable Long tournamentId) {
        try {
            Tournament tournament = tournamentService.getById(tournamentId);
            if (tournament != null) {
                return ResponseEntity.ok(tournament);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Tournament with ID " + tournamentId + " not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve tournament: " + e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllTournaments() {
        try {
            List<Tournament> tournaments = tournamentService.getAllTournaments();
            if (!tournaments.isEmpty()) {
                return ResponseEntity.ok(tournaments);
            } else {
                return ResponseEntity.ok("No tournaments found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve tournaments: " + e.getMessage());
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

    @PutMapping("/update/{tournamentId}")
    public ResponseEntity<String> updateTournament(
            @PathVariable Long tournamentId,
            @RequestBody Tournament updatedTournament) {
        try {
            tournamentService.updateTournament(tournamentId, updatedTournament);
            return ResponseEntity.ok("Tournament updated successfully!");
        } catch (TournamentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tournament not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update tournament: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{tournamentId}")
    public ResponseEntity<String> deleteTournament(@PathVariable Long tournamentId) {
        try {
            tournamentService.deleteTournament(tournamentId);
            return ResponseEntity.ok("Tournament deleted successfully!");
        } catch (TournamentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tournament not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete tournament: " + e.getMessage());
        }
    }
}
