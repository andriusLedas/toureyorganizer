package lt.codeacademy.javau7.tournament_organizer.services;

import jakarta.transaction.Transactional;
import lt.codeacademy.javau7.tournament_organizer.dtos.ParticipantNameDTO;
import lt.codeacademy.javau7.tournament_organizer.exceptions.TournamentNotFoundException;
import lt.codeacademy.javau7.tournament_organizer.models.Tournament;

import java.util.List;

public interface TournamentService {
    @Transactional
    void createTournament(Long userId, Tournament tournament) throws Exception;

    void saveTournament(Tournament tournament);

    List<Tournament> getAllUserTournaments(Long userId) throws Exception;

    List<Tournament> getAllTournaments();

    Tournament getById(Long tournamentId) throws TournamentNotFoundException;

    @Transactional
    void updateTournament(Long tournamentId, Tournament updatedTournament) throws TournamentNotFoundException;

    @Transactional
    void deleteTournament(Long tournamentId) throws TournamentNotFoundException;

}
