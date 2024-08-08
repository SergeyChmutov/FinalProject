package ru.skypro.homework.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginDTO {

    @JsonProperty("username")
    @Size(min = 4, max = 32)
    private String userName;
    @Size(min = 8, max = 16)
    private String password;
}
