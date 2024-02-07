package com.library_management.api.controller;

import com.library_management.api.dto.LoginDTO;
import com.library_management.api.model.User;
import com.library_management.api.service.AuthService;
import com.library_management.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Endpoint to login")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "login a user",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = User.class))) }),
            @ApiResponse(responseCode = "400", description = "Invalid json",
                    content = @Content) })
    public ResponseEntity<String> login(@RequestBody LoginDTO loginDTO){

        return ResponseEntity.ok().body(authService.login(loginDTO.getEmail(), loginDTO.getPassword()));
    }

    @PostMapping("/signup")
    @Operation(summary = "Endpoint to signup")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "login a signup",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = User.class))) }),
            @ApiResponse(responseCode = "400", description = "Invalid json",
                    content = @Content) })
    public ResponseEntity<String> signup(@RequestBody User user){
        authService.signup(user);
        return ResponseEntity.ok().body("User created successfully");
    }

}
