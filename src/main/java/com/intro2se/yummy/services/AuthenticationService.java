package com.intro2se.yummy.services;

import com.intro2se.yummy.dtos.request.*;
import com.intro2se.yummy.dtos.response.*;
import com.intro2se.yummy.entities.Customer;
import com.intro2se.yummy.entities.DeliveryDriver;
import com.intro2se.yummy.entities.RestaurantOwner;
import com.intro2se.yummy.entities.User;
import com.intro2se.yummy.enums.UserType;
import com.intro2se.yummy.exceptions.AppException;
import com.intro2se.yummy.exceptions.ErrorCode;
import com.intro2se.yummy.repositories.CustomerRepository;
import com.intro2se.yummy.repositories.DeliveryDriverRepository;
import com.intro2se.yummy.repositories.RestaurantOwnerRepository;
import com.intro2se.yummy.repositories.UserRepository;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationService {
    UserRepository userRepository;
    RestaurantOwnerRepository restaurantOwnerRepository;
    DeliveryDriverRepository deliveryDriverRepository;
    CustomerRepository customerRepository;
    PasswordEncoder passwordEncoder;

    @Value("${jwt.signerKey}")
    @NonFinal
    protected String SIGNER_KEY;

    public UserResponse registerUser(RegisterRequest request) {
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

    public RestaurantOwnerResponse completeRestaurantOwner(RestaurantOwnerCreationRequest request) {
        request.setUser(userRepository.findByUsername(request.getUsername())
                .orElseThrow(
                        () -> new AppException(ErrorCode.USER_NOT_FOUND)
                )
        );
        RestaurantOwner restaurantOwner = request.toRestaurantOwner();

        return RestaurantOwnerResponse.fromRestaurantOwner(restaurantOwnerRepository.save(restaurantOwner));
    }

    public DeliveryDriverResponse completeDeliveryDriver(DeliveryDriverCreationRequest request) {
        request.setUser(userRepository.findByUsername(request.getUsername())
                .orElseThrow(
                        () -> new AppException(ErrorCode.USER_NOT_FOUND)
                )
        );
        DeliveryDriver deliveryDriver = request.toDeliveryDriver();

        return DeliveryDriverResponse.fromDeliveryDriver(deliveryDriverRepository.save(deliveryDriver));
    }

    public CustomerResponse completeCustomer(CustomerCreationRequest request) {
        User user = userRepository.findByUsername(request.getUsername()).orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        if (user.getUserType() != UserType.CUSTOMER) {
            throw new AppException(ErrorCode.CUSTOMER_NOT_FOUND);
        }

        request.setUser(user);
        Customer customer = request.toCustomer();

        return CustomerResponse.fromCustomer(customerRepository.save(customer));
    }

    public AuthenticateResponse authenticate(AuthenticateRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(
                () -> new AppException(ErrorCode.USER_NOT_FOUND)
        );

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        return AuthenticateResponse.builder()
                .token(generateToken(user))
                .userType(user.getUserType().toString())
                .build();
    }

    private String generateToken(User user) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);

        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer("Yummy-backend-team")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, ChronoUnit.DAYS).toEpochMilli()
                ))
                .claim("scope", user.getUserType())
                .build();

        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
}
