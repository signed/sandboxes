package com.github.signed.demo.http;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;

import java.math.BigDecimal;

import static com.google.common.truth.Truth.assertThat;

@JsonTest
class BusinessLogicControllerJSONTests {

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void requestDto() throws JsonProcessingException {
        final String requestWithAllProperties = """
                {
                  "product": "sample product",
                  "quantity": 2
                }""";
        final BusinessLogicController.BusinessLogicRequestDTO dto = objectMapper.readValue(requestWithAllProperties, BusinessLogicController.BusinessLogicRequestDTO.class);

        assertThat(dto.product).isEqualTo("sample product");
        assertThat(dto.quantity).isEqualTo(2);
    }

    @Test
    void responseDto() throws JsonProcessingException {
        final BusinessLogicController.BusinessLogicResponseDTO dto = new BusinessLogicController.BusinessLogicResponseDTO(BigDecimal.valueOf(34));
        final String s = objectMapper.writeValueAsString(dto);
        assertThat(s).isEqualTo("{\"price\":34}");
    }
}
