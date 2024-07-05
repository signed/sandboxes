package com.github.signed.demo.http;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


class BusinessLogicRequestDTO_ValidationTest {

    @Test
    void doNotProduceValidationErrorForAValidDto() {
        assertThat(validationErrorMessagesFor(anyValidBusinessLogicRequestDTO())).isEmpty();
    }

    @Test
    void productHasToBePresentAndNotEmpty() {
        final BusinessLogicRequestDTO dto = anyValidBusinessLogicRequestDTO();
        dto.product = null;

        assertThat(validationErrorMessagesFor(dto)).contains("must not be null");

        dto.product = "";
        assertThat(validationErrorMessagesFor(dto)).contains("must not be empty");
    }

    @Test
    void quantityHasToBeAPositiveInt() {
        final BusinessLogicRequestDTO dto = anyValidBusinessLogicRequestDTO();
        dto.quantity = 0;

        assertThat(validationErrorMessagesFor(dto)).contains("must be greater than 0");
    }


    private List<String> validationErrorMessagesFor(BusinessLogicRequestDTO dto) {
        final Set<ConstraintViolation<BusinessLogicRequestDTO>> result = validator().validate(dto);
        return result.stream().map(ConstraintViolation::getMessage).collect(Collectors.toList());
    }

    private Validator validator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static BusinessLogicRequestDTO anyValidBusinessLogicRequestDTO() {
        final BusinessLogicRequestDTO dto = new BusinessLogicRequestDTO();
        dto.product = "valid product name";
        dto.quantity = 42;
        return dto;
    }
}


