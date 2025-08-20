package com.intro2se.yummy.dtos.request;

import com.intro2se.yummy.entities.User;
import com.intro2se.yummy.enums.UserType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterRequest {
    @NotBlank(message = "INVALID_USERNAME")
    @Size(
            min = 2,
            max = 50,
            message = "INVALID_USERNAME"
    )
    String username;

    @NotBlank(message = "INVALID_PASSWORD")
    @Size(
            min = 8,
            max = 20,
            message = "INVALID_PASSWORD"
    )
    String password;

    @NotBlank(message = "INVALID_EMAIL")
    String email;

    @NotBlank(message = "INVALID_FULLNAME")
    String fullName;

    @NotBlank(message = "INVALID_PHONE_NUMBER")
    String phone;

    UserType userType;

    public User toUser() {
        User user = new User();

        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setFullName(fullName);
        user.setPhoneNumber(phone);
        user.setUserType(userType);

        return user;
    }
}
