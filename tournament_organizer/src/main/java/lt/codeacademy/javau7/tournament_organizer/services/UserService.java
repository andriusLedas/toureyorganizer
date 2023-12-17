package lt.codeacademy.javau7.tournament_organizer.services;

import jakarta.transaction.Transactional;
import lt.codeacademy.javau7.tournament_organizer.dto.UserDTO;
import lt.codeacademy.javau7.tournament_organizer.exceptions.UserNotFoundException;
import lt.codeacademy.javau7.tournament_organizer.models.Role;
import lt.codeacademy.javau7.tournament_organizer.models.User;
import lt.codeacademy.javau7.tournament_organizer.security.payload.SignupRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Set;

public interface UserService {
    ResponseEntity<?> registerUser(SignupRequest signUpRequest);

    User createUser(SignupRequest signUpRequest);

    Set<Role> mapRoles(Set<String> strRoles);

    boolean usernameExists(String username);

    boolean emailExists(String email);

    User getById(Long id);

    List<UserDTO> getAllUsers();

    @Transactional
    void updateUser(Long id, UserDTO updatedUserDTO) throws UserNotFoundException;

    void saveUser(User user);


    @Transactional
    void deleteUser(Long id);

    boolean canDeleteUser(Long userId, UserDetailsImpl currentUser);

    Long getIdFromUsername(String userName);
}

