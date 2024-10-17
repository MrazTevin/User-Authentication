package com.adamur.user_management.dto.user;

import com.adamur.user_management.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetDTO {

    private String token;
    private User user;
    private Date expiryDate;

    public PasswordResetDTO(String token, String email, Date expiryDate) {
    }
}
