package lt.codeacademy.javau7.tournament_organizer.repositories;

import lt.codeacademy.javau7.tournament_organizer.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
}
