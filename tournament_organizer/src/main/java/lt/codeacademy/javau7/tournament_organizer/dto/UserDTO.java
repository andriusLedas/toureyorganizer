package lt.codeacademy.javau7.tournament_organizer.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lt.codeacademy.javau7.tournament_organizer.utils.UserRole;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;
    private UserRole userRole;
    private String username;
    private String email;
    private String password;

    public UserDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public UserDTO(UserRole userRole, String username, String email, String password) {
        this.userRole = userRole;
        this.username = username;
        this.email = email;
        this.password = password;
    }
}

