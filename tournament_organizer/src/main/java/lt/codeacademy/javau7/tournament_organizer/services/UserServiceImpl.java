package lt.codeacademy.javau7.tournament_organizer.services;
import jakarta.transaction.Transactional;
import lt.codeacademy.javau7.tournament_organizer.dto.UserDTO;
import lt.codeacademy.javau7.tournament_organizer.exceptions.UserNotFoundException;
import lt.codeacademy.javau7.tournament_organizer.models.ERole;
import lt.codeacademy.javau7.tournament_organizer.models.Role;
import lt.codeacademy.javau7.tournament_organizer.models.User;
import lt.codeacademy.javau7.tournament_organizer.repositories.RoleRepository;
import lt.codeacademy.javau7.tournament_organizer.repositories.UserRepository;
import lt.codeacademy.javau7.tournament_organizer.security.payload.MessageResponse;
import lt.codeacademy.javau7.tournament_organizer.security.payload.SignupRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public ResponseEntity<?> registerUser(SignupRequest signUpRequest) {
        if (usernameExists(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (emailExists(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        User user = createUser(signUpRequest);
        Set<Role> roles = mapRoles(signUpRequest.getRole());

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @Override
    public User createUser(SignupRequest signUpRequest) {
        String encodedPassword = encoder.encode(signUpRequest.getPassword());
        return new User(signUpRequest.getUsername(), signUpRequest.getEmail(), encodedPassword);
    }

    @Override
    public Set<Role> mapRoles(Set<String> strRoles) {
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            log.info("Mapping roles for null input: defaulting to ROLE_USER");
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                Role userRole = switch (role) {
                    case "admin" -> {
                        log.info("Mapping role 'admin'");
                        yield roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                    }
                    case "user" -> {
                        log.info("Mapping role 'user'");
                        yield roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                    }
                    default -> {
                        log.info("Mapping role 'guest'");
                        yield roleRepository.findByName(ERole.ROLE_GUEST)
                                .orElseThrow(() -> new RuntimeException("Error: Role not found."));
                    }
                };
                roles.add(userRole);
            });
        }
        return roles;
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public User getById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        if (users.isEmpty()) {
            throw new IllegalStateException("No users found");
        }

        return users.stream()
                .map(user -> new UserDTO(user.getId(), user.getUsername()))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void updateUser(Long id, UserDTO updatedUserDTO) throws UserNotFoundException {
        User existingUser = getById(id);

        existingUser.setUsername(updatedUserDTO.getUsername());
        existingUser.setEmail(updatedUserDTO.getEmail());
        existingUser.setPassword(updatedUserDTO.getPassword());

        userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {

            User user = getById(id);
            userRepository.delete(user);
    }

    @Override
    public boolean canDeleteUser(Long userId, UserDetailsImpl currentUser) {

        User userToDelete = getById(userId);
        return userToDelete != null && currentUser.getId().equals(userId);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Long getIdFromUsername(String userName) {
        Optional<User> user = userRepository.findByUsername(userName);
        if (user.isPresent()) {
            return user.get().getId();
        }
        throw new UserNotFoundException("User with name " + userName + " not found.");
    }
}



