package lt.codeacademy.javau7.tournament_organizer.services;

import jakarta.transaction.Transactional;
import lt.codeacademy.javau7.tournament_organizer.dto.StageDTO;
import lt.codeacademy.javau7.tournament_organizer.dto.TournamentDTO;
import lt.codeacademy.javau7.tournament_organizer.exceptions.TournamentNotFoundException;
import lt.codeacademy.javau7.tournament_organizer.exceptions.UserNotFoundException;
import lt.codeacademy.javau7.tournament_organizer.models.Tournament;

import java.util.List;

public interface TournamentService {

    @Transactional
    void createTournament(Long userId, TournamentDTO tournamentDTO) throws UserNotFoundException;

    void saveTournament(Tournament tournament);

    Tournament getById(Long tournamentId) throws TournamentNotFoundException;

    List<TournamentDTO> getAllUserTournaments(Long userId) throws Exception;

    List<TournamentDTO> getAllTournaments();

    @Transactional
    void updateTournament(Long tournamentId, TournamentDTO updatedTournamentDTO) throws TournamentNotFoundException;

    @Transactional
    void addParticipants(Long tournamentId, List<String> participants);

    @Transactional
    void deleteTournament(Long tournamentId) throws TournamentNotFoundException;

    List<StageDTO> getStageAndMatchData(Long tournamentId) throws TournamentNotFoundException;
}
