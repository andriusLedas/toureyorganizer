package lt.codeacademy.javau7.tournament_organizer.services;

import lt.codeacademy.javau7.tournament_organizer.dto.ParticipantNameDTO;
import lt.codeacademy.javau7.tournament_organizer.models.Participant;


public interface ParticipantService {
    void save(Participant participant);

    void processParticipantList(Long tournamentId, ParticipantNameDTO participantDTO);

    void validateParticipantListSize(Long tournamentId, ParticipantNameDTO participantDTO);
}
