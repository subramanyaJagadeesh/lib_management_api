package com.library_management.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
//@Entity(name = "user")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String email;

    @Column(columnDefinition = "String default null")
    private String phone;

    @Column(nullable = false)
    private Integer type;

    @Column(nullable = false)
    private String password;

}
