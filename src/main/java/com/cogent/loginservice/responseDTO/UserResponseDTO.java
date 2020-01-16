package com.cogent.loginservice.responseDTO;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO implements Serializable {
    private Long id;
    private String password;
    private Integer loginAttempt;
    private Date createdAt;
    private Date updatedAt;
    private String username;
    private String name;
    private String medicalSpeciality;
    private String roles;
    private int active;
    private int isGp;

    private List<String> rolesList = new ArrayList<>();
}
