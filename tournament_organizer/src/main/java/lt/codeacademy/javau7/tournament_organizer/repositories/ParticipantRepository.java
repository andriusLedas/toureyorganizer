package lt.codeacademy.javau7.tournament_organizer.repositories;

import lt.codeacademy.javau7.tournament_organizer.models.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}
