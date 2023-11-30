package lt.codeacademy.javau7.tournament_organizer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lt.codeacademy.javau7.tournament_organizer.enums.UserRole;
import lt.codeacademy.javau7.tournament_organizer.serialization.UserRoleDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    /*The objectMapper() code ensures that even if the value of the query variable
       for userRole ENUM is posted in lowercase, the query will still go through,
       as they will be converted to uppercase.
    */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new SimpleModule().addDeserializer(
                UserRole.class, new UserRoleDeserializer()));
        return objectMapper;
    }
}