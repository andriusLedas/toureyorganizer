package lt.codeacademy.javau7.tournament_organizer.serialization;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lt.codeacademy.javau7.tournament_organizer.utils.UserRole;

import java.io.IOException;

/*This class ensures that even if the value of the query variable
     for userRole ENUM is posted in lowercase, the query will still go through,
     as they will be converted to uppercase.
    */
public class UserRoleDeserializer extends StdDeserializer<UserRole> {

    public UserRoleDeserializer() {
        super(UserRole.class);
    }

    @Override
    public UserRole deserialize(@org.jetbrains.annotations.NotNull JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);
        String role = node.asText().toUpperCase();
        return UserRole.valueOf(role);
    }
}