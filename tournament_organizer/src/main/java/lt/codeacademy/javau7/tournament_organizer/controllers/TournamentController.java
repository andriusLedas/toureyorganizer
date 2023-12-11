package lt.codeacademy.javau7.tournament_organizer.controllers;

import lt.codeacademy.javau7.tournament_organizer.dto.StageDTO;
import lt.codeacademy.javau7.tournament_organizer.dto.TournamentDTO;
import lt.codeacademy.javau7.tournament_organizer.exceptions.TournamentNotFoundException;
import lt.codeacademy.javau7.tournament_organizer.models.Tournament;
import lt.codeacademy.javau7.tournament_organizer.services.TournamentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/tournaments")
public class TournamentController {

    TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    // DTO body: name (string), numParticipants (int)
    @PostMapping("/create/{userId}")
    public ResponseEntity<String> createTournament(
            @PathVariable Long userId,
            @RequestBody TournamentDTO tournamentDTO) {

        try {
            tournamentService.createTournament(userId, tournamentDTO);
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
                TournamentDTO tournamentDTO = new  TournamentDTO(tournament);
                return ResponseEntity.ok(tournamentDTO);
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
            List<TournamentDTO> tournamentDTOs = tournamentService.getAllTournaments();
            if (!tournamentDTOs.isEmpty()) {
                return ResponseEntity.ok(tournamentDTOs);
            } else {
                return ResponseEntity.ok("No tournaments found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve tournaments: " + e.getMessage());
        }
    }

    @GetMapping("/getUserTournaments/{userId}")
    public ResponseEntity<?> getUserTournaments(@PathVariable Long userId) {
        try {
            List<TournamentDTO> tournamentDTOs = tournamentService.getAllUserTournaments(userId);
            if (!tournamentDTOs.isEmpty()) {
                return ResponseEntity.ok(tournamentDTOs);
            } else {
                return ResponseEntity.ok("No tournaments found for user with ID: " + userId);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve user tournaments: " + e.getMessage());
        }
    }

    @GetMapping("/StageAndMatchData/{tournamentId}")
    public ResponseEntity<?> getStageAndMatchData(@PathVariable Long tournamentId) {
        try {
            List<StageDTO> stages = tournamentService.getStageAndMatchData(tournamentId);
            return ResponseEntity.ok(stages);
        } catch (TournamentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tournament not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve stage and match data: " + e.getMessage());
        }
    }
    //DTO body: name (string)
    @PutMapping("/update/{tournamentId}")
    public ResponseEntity<String> updateTournament(
            @PathVariable Long tournamentId,
            @RequestBody TournamentDTO updatedTournamentDTO) {
        try {
            tournamentService.updateTournament(tournamentId, updatedTournamentDTO);
            return ResponseEntity.ok("Tournament updated successfully!");
        } catch (TournamentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tournament not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update tournament: " + e.getMessage());
        }
    }
    //DTO body: [ "Team1", "Team2", etc.]
    @PostMapping("/{tournamentId}/addParticipants")
    public ResponseEntity<String> addParticipantsToTournament(
            @PathVariable Long tournamentId,
            @RequestBody List<String> participants) {
        try {
            tournamentService.addParticipants(tournamentId, participants);
            return ResponseEntity.ok("Participants added successfully!");
        } catch (TournamentNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Tournament not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to add participants: " + e.getMessage());
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
