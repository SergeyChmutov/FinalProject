package ru.skypro.homework.dto;

import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class NewPasswordDTO {

    @Size(min = 8, max = 16)
    private String currentPassword;
    @Size(min = 8, max = 16)
    private String newPassword;
}
