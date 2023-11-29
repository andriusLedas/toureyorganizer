package lt.codeacademy.javau7.tournament_organizer.repositories;

import lt.codeacademy.javau7.tournament_organizer.models.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StageRepository extends JpaRepository<Stage, Long> {
}
