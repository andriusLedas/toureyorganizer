package lt.codeacademy.javau7.tournament_organizer.services;

import jakarta.transaction.Transactional;
import lt.codeacademy.javau7.tournament_organizer.models.Tournament;
import org.springframework.stereotype.Service;


public interface MatchService {
    @Transactional
    void generateFirstStageMatches(Tournament tournament);
}
