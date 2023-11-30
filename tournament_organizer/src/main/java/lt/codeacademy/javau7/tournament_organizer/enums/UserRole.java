package lt.codeacademy.javau7.tournament_organizer.enums;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lt.codeacademy.javau7.tournament_organizer.serialization.UserRoleDeserializer;

/* The @JsonDeserialize annotation ensures that query variable values for UserRole
    that are posted in lowercase will be converted to uppercase
 */
@JsonDeserialize(using = UserRoleDeserializer.class)
public enum UserRole {
    ADMINISTRATOR,
    GUEST,
    USER
}