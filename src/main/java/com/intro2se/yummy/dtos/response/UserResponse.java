package com.intro2se.yummy.dtos.response;

import com.intro2se.yummy.entities.User;
import com.intro2se.yummy.enums.UserType;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    @Setter(AccessLevel.NONE)
    Integer id;
    String username;
    String email;
    String fullName;
    String phone;
    UserType userType;

    public User toUser() {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setFullName(fullName);
        user.setPhoneNumber(phone);
        user.setUserType(userType);
        return user;
    }

    public static UserResponse fromUser(User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getFullName(),
                user.getPhoneNumber(),
                user.getUserType()
        );
    }
}
