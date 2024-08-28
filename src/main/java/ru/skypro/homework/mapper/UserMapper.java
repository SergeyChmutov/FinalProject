package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.springframework.security.core.userdetails.UserDetails;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.model.User;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {ru.skypro.homework.enums.Role.class,
                ru.skypro.homework.model.UserAvatar.class,
                java.lang.String.class
        }
)
public interface UserMapper {

    @Mappings(value = {
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "email", expression = "java(register.getUserName().toLowerCase())"),
            @Mapping(target = "image", ignore = true)
    })
    User registerDTOToUser(RegisterDTO register);

    UserDTO userToUserDTO(User user);

    @Mappings(value = {
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "email", ignore = true),
            @Mapping(target = "password", ignore = true),
            @Mapping(target = "role", ignore = true),
            @Mapping(target = "image", ignore = true)
    })
    User updateUserDTOToUser(UpdateUserDTO userDTO);

}
