package com.library_management.api.controller;

import com.library_management.api.model.User;
import com.library_management.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    @Operation(summary = "Endpoint to fetch all users of a type")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "fetch all users of one type",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = User.class))) }),
            @ApiResponse(responseCode = "400", description = "Invalid type",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No Users found",
                    content = @Content) })
    public ResponseEntity<List<User>> getAllUsers(@RequestParam Boolean type) {
        return ResponseEntity.ok().body(userService.getAllUsers(type));
    }

    @GetMapping("/:id")
    @Operation(summary = "Endpoint to fetch a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "fetch a user",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = User.class))) }),
            @ApiResponse(responseCode = "400", description = "Invalid type",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No User found",
                    content = @Content) })
    public ResponseEntity<User> getUser() {
        Long userId = ((User) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getId();
        return ResponseEntity.ok().body(userService.getUser(userId));
    }

    @PostMapping("/delete")
    @Operation(summary = "Endpoint to delete a user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "delete a user",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = User.class))) }),
            @ApiResponse(responseCode = "400", description = "Invalid json",
                    content = @Content) })
    public ResponseEntity<String> deleteUser(@RequestParam() Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.ok().body("User deleted successfully");
    }

}
