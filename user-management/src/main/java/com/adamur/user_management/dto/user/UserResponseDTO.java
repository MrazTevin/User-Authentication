package com.adamur.user_management.dto.user;

import lombok.Data;

import java.util.Date;

@Data
public class UserResponseDTO {
    private Long id;
    private String email;
    private Boolean isVerified;
    private Date createdAt;
    private String message;
}
