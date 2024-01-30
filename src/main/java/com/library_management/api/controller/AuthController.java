package com.library_management.api.controller;

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
    AuthService authService;

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
    public ResponseEntity<String> login(@RequestBody String email, @RequestBody String password){

        return ResponseEntity.ok().body(authService.login(email, password));
    }

    @PostMapping("/signup")
    @Operation(summary = "Endpoint to signup")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "login a signup",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema( schema = @Schema(implementation = User.class))) }),
            @ApiResponse(responseCode = "400", description = "Invalid json",
                    content = @Content) })
    public ResponseEntity<String> signup(
            @RequestBody String firstName,
            @RequestBody String lastName,
            @RequestBody String phone,
            @RequestBody String email,
            @RequestBody String password
    ){
        userService.createUser(firstName, lastName, phone, email, 0, password);
        return ResponseEntity.ok().body("User created successfully");
    }

}
