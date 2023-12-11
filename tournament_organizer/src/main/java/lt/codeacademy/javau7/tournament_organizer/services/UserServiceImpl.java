package lt.codeacademy.javau7.tournament_organizer.services;
import jakarta.transaction.Transactional;
import lt.codeacademy.javau7.tournament_organizer.dto.UserDTO;
import lt.codeacademy.javau7.tournament_organizer.exceptions.InvalidCredentialsException;
import lt.codeacademy.javau7.tournament_organizer.exceptions.UserNotFoundException;
import lt.codeacademy.javau7.tournament_organizer.exceptions.UsernameAlreadyExistsException;
import lt.codeacademy.javau7.tournament_organizer.models.User;
import lt.codeacademy.javau7.tournament_organizer.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void createUser(UserDTO userDTO) {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new UsernameAlreadyExistsException("Username already exists");
        }

        User user = new User();
        user.setUserRole(userDTO.getUserRole());
        user.setUsername(userDTO.getUsername());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        userRepository.save(user);
    }
    @Override
    public void saveUser(User user) {userRepository.save(user);
    }

    @Override
    public Long authenticateUser(UserDTO userDTO) throws InvalidCredentialsException {
        User user = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if (!user.getPassword().equals(userDTO.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        return user.getId();
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

        existingUser.setUserRole(updatedUserDTO.getUserRole());
        existingUser.setUsername(updatedUserDTO.getUsername());
        existingUser.setEmail(updatedUserDTO.getEmail());
        existingUser.setPassword(updatedUserDTO.getPassword());

        userRepository.save(existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) throws UserNotFoundException {
        User user = getById(id);
        userRepository.delete(user);
    }
}

