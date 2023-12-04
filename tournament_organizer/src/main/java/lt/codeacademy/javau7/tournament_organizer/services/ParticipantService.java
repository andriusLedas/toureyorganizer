package lt.codeacademy.javau7.tournament_organizer.services;

import lt.codeacademy.javau7.tournament_organizer.dtos.ParticipantNameDTO;


public interface ParticipantService {
    void processParticipantList(Long tournamentId, ParticipantNameDTO participantDTO);

    void validateParticipantListSize(Long tournamentId, ParticipantNameDTO participantDTO);
}
