package lt.codeacademy.javau7.tournament_organizer.controllers;

import lt.codeacademy.javau7.tournament_organizer.exceptions.UserNotFoundException;
import lt.codeacademy.javau7.tournament_organizer.models.User;
import lt.codeacademy.javau7.tournament_organizer.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            return ResponseEntity.ok("User created successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to create user: " + e.getMessage());
        }
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        try {
            User user = userService.getById(userId);
            if (user != null) {
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User with ID " + userId + " not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve user: " + e.getMessage());
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllUsers() {
        try {
            List<User> users = userService.getAllUsers();
            if (!users.isEmpty()) {
                return ResponseEntity.ok(users);
            } else {
                return ResponseEntity.ok("No users found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve users: " + e.getMessage());
        }
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateUser(
            @PathVariable Long userId,
            @RequestBody User updatedUser) {
        try {
            userService.updateUser(userId, updatedUser);
            return ResponseEntity.ok("User updated successfully!");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to update user: " + e.getMessage());
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.ok("User deleted successfully!");
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("User not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to delete user: " + e.getMessage());
        }
    }
}
