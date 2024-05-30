package com.ra.validator.user;

import com.ra.model.repository.ProductRepository;
import com.ra.model.repository.UserRepository;
import com.ra.validator.product.NameProductUnique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsernameValidator implements ConstraintValidator<UsernameUnique, String> {
    private final UserRepository userRepository;

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        return !userRepository.existsByUsername(string);
    }
}
