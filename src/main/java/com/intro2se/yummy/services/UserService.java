package com.intro2se.yummy.services;

import com.intro2se.yummy.dtos.request.UserCreationRequest;
import com.intro2se.yummy.dtos.request.UserUpdateRequest;
import com.intro2se.yummy.dtos.response.UserResponse;
import com.intro2se.yummy.entities.User;
import com.intro2se.yummy.exceptions.AppException;
import com.intro2se.yummy.exceptions.ErrorCode;
import com.intro2se.yummy.repositories.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users.stream().map(UserResponse::fromUser).toList();
    }

    public UserResponse createUser(UserCreationRequest request) {
        User user = request.toUser();

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new AppException(ErrorCode.USER_ALREADY_EXISTS);
        }

        if (userRepository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_ALREADY_USED);
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_USED);
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return UserResponse.fromUser(userRepository.save(user));
    }

    public UserResponse getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );
        return UserResponse.fromUser(user);
    }

    public UserResponse updateUserByUsername(String username, UserUpdateRequest request) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        if (!request.getEmail().equals(user.getEmail()) && userRepository.existsByEmail(request.getEmail())) {
            throw new AppException(ErrorCode.EMAIL_ALREADY_USED);
        }

        if (!request.getPhone().equals(user.getPhoneNumber()) && userRepository.existsByPhoneNumber(request.getPhone())) {
            throw new AppException(ErrorCode.PHONE_NUMBER_ALREADY_USED);
        }

        user = request.mapToUser(user);
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        return UserResponse.fromUser(userRepository.save(user));
    }
}
