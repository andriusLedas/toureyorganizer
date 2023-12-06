package lt.codeacademy.javau7.tournament_organizer.services;

import lt.codeacademy.javau7.tournament_organizer.dto.ParticipantNameDTO;
import lt.codeacademy.javau7.tournament_organizer.models.Participant;
import lt.codeacademy.javau7.tournament_organizer.models.Tournament;
import lt.codeacademy.javau7.tournament_organizer.repositories.ParticipantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    ParticipantRepository participantRepository;
    TournamentService tournamentService;
    private static final Logger logger = LoggerFactory.getLogger(ParticipantServiceImpl.class);

    public ParticipantServiceImpl(ParticipantRepository participantRepository, TournamentService tournamentService) {
        this.participantRepository = participantRepository;
        this.tournamentService = tournamentService;
    }

    @Override
    public void save(Participant participant) {
        participantRepository.save(participant);
    }

    @Override
    public void processParticipantList(Long tournamentId, ParticipantNameDTO participantDTO) {
        try {
            List<Participant> participants;
            List<String> participantNames = participantDTO.getParticipantNames();
            Tournament tournament = tournamentService.getById(tournamentId);

            participants = participantNames.stream()
                    .map(name -> new Participant(name, tournament))
                    .toList();

            participantRepository.saveAll(participants);
            tournament.setParticipants(participants);
            tournamentService.saveTournament(tournament);

            logger.info("Participants successfully assigned to the tournament (ID: {})", tournamentId);
        } catch (Exception e) {
            logger.error("Failed to process participant list for tournament (ID: {}): {}", tournamentId, e.getMessage());
            // Rethrow or handle the exception as needed
        }
    }
    //Method called by ParticipantController for incoming list of participant names
    @Override
    public void validateParticipantListSize(Long tournamentId, ParticipantNameDTO participantDTO) {
        Tournament tournament = tournamentService.getById(tournamentId);
        int expectedNumParticipants = tournament.getNumParticipants();

        if (participantDTO.getParticipantNames().size() != expectedNumParticipants) {
            throw new IllegalArgumentException("Invalid number of participants. Expected: " + expectedNumParticipants);
        }
    }
}
