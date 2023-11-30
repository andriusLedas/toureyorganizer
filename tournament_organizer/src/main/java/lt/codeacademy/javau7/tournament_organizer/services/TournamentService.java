package lt.codeacademy.javau7.tournament_organizer.services;

import jakarta.transaction.Transactional;
import lt.codeacademy.javau7.tournament_organizer.models.Tournament;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TournamentService {
    @Transactional
    void createTournament(Long userId, Tournament tournament) throws Exception;

    List<Tournament> getAllUserTournaments(Long userId) throws Exception;
}
