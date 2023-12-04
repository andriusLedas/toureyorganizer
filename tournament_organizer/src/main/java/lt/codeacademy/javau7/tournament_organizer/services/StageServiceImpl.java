package lt.codeacademy.javau7.tournament_organizer.services;

import lt.codeacademy.javau7.tournament_organizer.models.Stage;
import lt.codeacademy.javau7.tournament_organizer.repositories.StageRepository;
import org.springframework.stereotype.Service;
@Service
public class StageServiceImpl implements StageService{

        StageRepository stageRepository;

        public StageServiceImpl(StageRepository stageRepository) {
            this.stageRepository = stageRepository;
        }
        @Override
        public void save(Stage stage){
            stageRepository.save(stage);
        }

    }
