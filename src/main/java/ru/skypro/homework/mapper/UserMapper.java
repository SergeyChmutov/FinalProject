package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;
import org.springframework.security.core.userdetails.UserDetails;
import ru.skypro.homework.dto.RegisterDTO;
import ru.skypro.homework.model.User;

@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        imports = {ru.skypro.homework.enums.Role.class}
)
public interface UserMapper {

    @Mappings(value = {
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "email", source = "userName"),
            @Mapping(target = "image", ignore = true),
            @Mapping(target = "avatar", ignore = true)
    })
    User RegisterDTOToUser(RegisterDTO register);

    @Mappings(value = {
            @Mapping(target = "id", constant = "0"),
            @Mapping(target = "email", expression = "java(userDetails.getUsername())"),
            @Mapping(target = "firstName", constant = "firstName"),
            @Mapping(target = "lastName", constant = "lastName"),
            @Mapping(target = "phone", constant = "+7 (111) 111-11-11"),
            @Mapping(target = "role", expression = "java(Role.USER)"),
    })
    User UserDetailsToUser(UserDetails userDetails);

}
