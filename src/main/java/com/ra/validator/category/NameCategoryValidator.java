package com.ra.validator.category;

import com.ra.model.repository.BrandRepository;
import com.ra.model.repository.CategoryRepository;
import com.ra.validator.brand.EmailBrandUnique;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NameCategoryValidator implements ConstraintValidator<NameCategoryUnique, String> {
    private final CategoryRepository categoryRepository;

    @Override
    public boolean isValid(String string, ConstraintValidatorContext constraintValidatorContext) {
        return !categoryRepository.existsByCategoryName(string);
    }
}
