package com.intro2se.yummy.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthenticateRequest {
    @NotBlank(message = "EMPTY_USERNAME")
    String username;

    @NotBlank(message = "EMPTY_PASSWORD")
    String password;
}
