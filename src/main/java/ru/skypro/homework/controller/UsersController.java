package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;

import java.io.IOException;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Operation(tags = "Users", operationId = "setPassword")
    @PostMapping("/set_password")
    public ResponseEntity setPassword(@RequestBody NewPasswordDTO newPassword) {
        if (false) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(tags = "Users", operationId = "getUser")
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUser() {
        return ResponseEntity.ok(new UserDTO());
    }

    @Operation(tags = "Users", operationId = "updateUser")
    @PatchMapping("/me")
    public ResponseEntity<UpdateUserDTO> updateUser(@RequestBody UpdateUserDTO updateUser) {
        return ResponseEntity.ok(updateUser);
    }

    @Operation(tags = "Users", operationId = "updateUserImage")
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity updateUserImage(@RequestParam MultipartFile image) throws IOException {
        return ResponseEntity.ok().build();
    }

}
