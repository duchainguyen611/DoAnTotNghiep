package com.ra.validator.brand;

import com.ra.model.repository.BrandRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailBrandValidator implements ConstraintValidator<EmailBrandUnique, String> {
    private final BrandRepository brandRepository;

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        return !brandRepository.existsByEmail(string);
    }
}
