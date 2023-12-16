package lt.codeacademy.javau7.tournament_organizer.repositories;

import java.util.Optional;

import lt.codeacademy.javau7.tournament_organizer.models.ERole;
import lt.codeacademy.javau7.tournament_organizer.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}