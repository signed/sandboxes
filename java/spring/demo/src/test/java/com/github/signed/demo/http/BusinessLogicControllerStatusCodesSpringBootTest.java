package com.github.signed.demo.http;

import com.github.signed.demo.core.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
        , classes = BusinessLogicControllerStatusCodesSpringBootTest.Specific.class
)
@AutoConfigureMockMvc
class BusinessLogicControllerStatusCodesSpringBootTest {

    @TestConfiguration
    public static class Specific {

        @Bean
        @Primary
        public BusinessLogikFake businessLogicFake() {
            return new BusinessLogikFake();
        }
    }

    @Autowired
    private MockMvc mvc;

    @Test
    void replaceBusinessLogicWithMock(@Autowired BusinessLogikFake businessLogik) throws Exception {
        businessLogik.respondWith(BusinessLogicResult.success(new BusinessLogicResult.BusinessLogicOk(new Price(BigDecimal.TWO))));
        this.mvc.perform(post("/logic/verbose")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "product": "cheese",
                                  "quantity": 2
                                }"""))
                .andExpect(status().isOk());
    }
}
