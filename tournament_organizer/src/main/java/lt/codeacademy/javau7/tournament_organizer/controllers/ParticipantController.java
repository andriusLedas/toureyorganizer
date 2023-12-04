package lt.codeacademy.javau7.tournament_organizer.controllers;

import lt.codeacademy.javau7.tournament_organizer.dtos.ParticipantNameDTO;
import lt.codeacademy.javau7.tournament_organizer.services.ParticipantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/participants")
public class ParticipantController {
    private ParticipantService participantService;

    public ParticipantController(ParticipantService participantService) {
        this.participantService = participantService;
    }

    @PostMapping("/assignToTournament/{tournamentId}")
    public ResponseEntity<String> assignParticipantsToTournament(
            @PathVariable Long tournamentId,
            @RequestBody ParticipantNameDTO participantDTO) {
        try {
            participantService.validateParticipantListSize(tournamentId, participantDTO);

            participantService.processParticipantList(tournamentId, participantDTO);

            return ResponseEntity.ok("Participants successfully assigned to the tournament (ID: " + tournamentId + ")");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process participant list: " + e.getMessage());
        }
    }
}