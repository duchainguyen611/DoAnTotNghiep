package com.ra.validator.product;

import com.ra.model.repository.CategoryRepository;
import com.ra.model.repository.ProductRepository;
import com.ra.validator.category.NameCategoryUnique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NameProductValidator implements ConstraintValidator<NameProductUnique, String> {
    private final ProductRepository productRepository;

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        return !productRepository.existsByProductName(string);
    }
}
