package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPasswordDTO;
import ru.skypro.homework.dto.UpdateUserDTO;
import ru.skypro.homework.dto.UserDTO;
import ru.skypro.homework.mapper.UserMapper;
import ru.skypro.homework.service.UserAvatarService;
import ru.skypro.homework.service.UserService;
import ru.skypro.homework.model.User;

import java.io.IOException;

@Slf4j
@CrossOrigin(origins = "http://localhost:3000")
@AllArgsConstructor
@RestController
@RequestMapping("/users")
public class UsersController {

    private final UserMapper userMapper;
    private final UserService userService;
    private final UserAvatarService userAvatarService;

    @Operation(
            tags = "Users",
            operationId = "setPassword",
            summary = "Password update",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User`s new password info",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            }
    )
    @PostMapping("/set_password")
    public ResponseEntity setPassword(@RequestBody NewPasswordDTO newPassword) {
        log.info("New password: {}", newPassword);

        if (false) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @Operation(
            tags = "Users",
            operationId = "getUser",
            summary = "Getting information about an authorized user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UserDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            }
    )
    @GetMapping("/me")
    public ResponseEntity<UserDTO> getUser(Authentication authentication) {
        User user = userService.findByEmail(authentication.getName());

        return ResponseEntity.ok(userMapper.userToUserDTO(user));
    }

    @Operation(
            tags = "Users",
            operationId = "updateUser",
            summary = "Updating information about an authorized user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User`s new info",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE)
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = UpdateUserDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            }
    )
    @PatchMapping("/me")
    public ResponseEntity<UpdateUserDTO> updateUser(@RequestBody UpdateUserDTO updateUser) {
        return ResponseEntity.ok(updateUser);
    }

    @Operation(
            tags = "Users",
            operationId = "updateUserImage",
            summary = "Update the avatar of an authorized user",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User`s new avatar",
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE)
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            }
    )
    @PatchMapping(value = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity updateUserImage(@RequestParam MultipartFile image) throws IOException {
        return ResponseEntity.ok().build();
    }

    @Operation(
            tags = "Users",
            operationId = "getUserImage",
            summary = "Gets user avatar",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "OK",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE,
                                    array = @ArraySchema(schema = @Schema(implementation = byte.class))
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized",
                            content = @Content(schema = @Schema(hidden = true))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found",
                            content = @Content(schema = @Schema(hidden = true))
                    )
            }
    )
    @GetMapping("/me/image")
    public ResponseEntity<byte[]> getUserAvatar(Authentication authentication) {
        return userAvatarService.getUserAvatar(authentication.getName());
    }

}
