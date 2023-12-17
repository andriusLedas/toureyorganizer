package lt.codeacademy.javau7.tournament_organizer.services;

import jakarta.transaction.Transactional;
import lt.codeacademy.javau7.tournament_organizer.models.Stage;
import lt.codeacademy.javau7.tournament_organizer.models.Tournament;
import org.springframework.stereotype.Service;

import java.util.List;

public interface StageService {

    void save (Stage stage);

    @Transactional
    List<Stage> createStagesAndMatches(Tournament tournament);
}
